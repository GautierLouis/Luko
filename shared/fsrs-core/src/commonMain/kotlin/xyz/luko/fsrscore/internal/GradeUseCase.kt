package xyz.luko.fsrscore.internal

import xyz.luko.apicontracts.dto.AttemptSignal
import xyz.luko.apicontracts.dto.PracticeMode
import xyz.luko.apicontracts.dto.RecognitionResult
import xyz.luko.fsrscore.model.Grade
import xyz.luko.fsrscore.model.StrokeComparisonResult

internal object GradeUseCase {

    private const val FORGOT_THRESHOLD = 0.52f
    private const val HARD_THRESHOLD = 0.68f
    private const val GOOD_THRESHOLD = 0.84f
    private const val OVERALL_ACCURACY_FLOOR = 50f // to calibrate

    fun deriveGrade(
        signals: AttemptSignal,
        stroke: StrokeComparisonResult,
    ): Grade {
        // ── Layer 1: hard gates (recognition + structural failure) ──
        if (signals.recognitionResult == RecognitionResult.FAILURE ||
            !stroke.strokeCountCorrect
        ) {
            return Grade.FORGOT
        }

        // Geometry can still override a technically-passing recognition
        if (stroke.overallAccuracy < OVERALL_ACCURACY_FLOOR) {
            return Grade.FORGOT
        }

        if (signals.recognitionResult == RecognitionResult.PARTIAL ||
            !stroke.strokeOrderCorrect
        ) {
            return capByPracticeMode(Grade.HARD, signals.practiceMode)
        }

        // ── Layer 2: quality score (0..1) ──
        val targetMs = CharacterDurationUseCase.calculate(
            strokeCount = signals.rawReferenceMedians.size,
            practiceMode = signals.practiceMode,
            complexityFactor = signals.complexityFactor,
        )
        val speedRatio = signals.durationMs.toFloat() / targetMs.toFloat()

        val score = qualityScore(stroke, speedRatio)
        val rawGrade = scoreToGrade(score)

        // ── Layer 3: practice mode ceiling ──
        return capByPracticeMode(rawGrade, signals.practiceMode)
    }

    private fun qualityScore(stroke: StrokeComparisonResult, speedRatio: Float): Float {
        val worstStroke = stroke.strokeAccuracies.minOrNull() ?: 0f
        val d = stroke.details

        // orderAccuracy deliberately excluded: by this point strokeOrderCorrect has already
        // gated to true (orderAccuracy == 100f always), so including it here would be a dead,
        // non-discriminating term. Weights redistributed from the original 0.18 across the rest.
        val geometryScore = (
            stroke.overallAccuracy * 0.34f +
                worstStroke * 0.22f +
                d.pathSimilarity * 0.22f +
                d.directionAccuracy * 0.12f +
                d.startPointAccuracy * 0.05f +
                d.endPointAccuracy * 0.05f
            ) / 100f

        val speedScore = speedScore(speedRatio)

        return (geometryScore * 0.88f + speedScore * 0.12f).coerceIn(0f, 1f)
    }

    private data class SpeedPoint(val ratio: Float, val score: Float)

    // Continuous piecewise-linear curve, no hard jumps at any breakpoint.
    // Peak at ratio=1.0 (on-target timing); tapers on both sides.
    // To calibrate once real drawDurationMs data exists.
    private val speedCurve = listOf(
        SpeedPoint(0.0f, 0.85f),  // suspiciously fast still gets partial credit, not full
        SpeedPoint(0.75f, 0.95f),
        SpeedPoint(1.0f, 1.0f),
        SpeedPoint(1.4f, 0.75f),
        SpeedPoint(2.5f, 0.40f),
        SpeedPoint(3.5f, 0.25f), // floor reached smoothly, flat beyond this point
    )

    private fun speedScore(ratio: Float): Float {
        val clamped = ratio.coerceAtLeast(0f)

        if (clamped <= speedCurve.first().ratio) return speedCurve.first().score
        if (clamped >= speedCurve.last().ratio) return speedCurve.last().score

        for (i in 0 until speedCurve.size - 1) {
            val a = speedCurve[i]
            val b = speedCurve[i + 1]
            if (clamped in a.ratio..b.ratio) {
                val t = (clamped - a.ratio) / (b.ratio - a.ratio)
                return a.score + (b.score - a.score) * t
            }
        }
        return speedCurve.last().score // unreachable given the bounds checks above, kept as a safe fallback
    }

    private fun scoreToGrade(score: Float): Grade = when {
        score < FORGOT_THRESHOLD -> Grade.FORGOT
        score < HARD_THRESHOLD -> Grade.HARD
        score < GOOD_THRESHOLD -> Grade.GOOD
        else -> Grade.EASY
    }

    private fun capByPracticeMode(grade: Grade, mode: PracticeMode): Grade {
        val ceiling = when (mode) {
            PracticeMode.EASY -> Grade.HARD
            PracticeMode.MEDIUM -> Grade.GOOD
            PracticeMode.HARD -> Grade.EASY
        }
        return if (grade.severity <= ceiling.severity) grade else ceiling
    }
}

