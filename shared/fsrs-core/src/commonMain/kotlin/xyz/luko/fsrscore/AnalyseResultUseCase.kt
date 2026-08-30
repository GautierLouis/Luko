package xyz.luko.fsrscore

import xyz.luko.apicontracts.dto.AttemptSignal
import xyz.luko.fsrscore.internal.FsrsUseCase
import xyz.luko.fsrscore.internal.GradeUseCase
import xyz.luko.fsrscore.internal.StrokeComparisonUseCase
import xyz.luko.fsrscore.model.AnalysisResult
import kotlin.time.Clock

class AnalyseResultUseCase {

    fun analyse(signal: AttemptSignal): AnalysisResult {

        //run stroke comparison
        val comparisonResult = StrokeComparisonUseCase.calculate(
            reference = StrokeComparisonUseCase.strokeToComparableStroke(signal.referenceMedians),
            userStroke = StrokeComparisonUseCase.strokeToComparableStroke(signal.strokes),
        )

        //compute Gradle
        val grade = GradeUseCase.deriveGrade(
            signals = signal,
            stroke = comparisonResult
        )

        //then run fsrs
        val result = FsrsUseCase.compute(
            current = signal.fsrsState,
            grade = grade,
            elapsedDays = signal.fsrsState?.lastReviewedAt?.let { elapsedDaysUntilNow(it) } ?: 0.0
        )

        return AnalysisResult(
            strokeComparison = comparisonResult,
            grade = grade,
            fsrsResult = result
        )
    }

    /**
     * Calculate an elapsed number of that that passed between lastReviewMilli until now
     */
    private fun elapsedDaysUntilNow(from: Long): Double {
        val now = Clock.System.now().toEpochMilliseconds()
        return ((now - from).toDouble() / 86_400_000L).coerceAtLeast(0.0)
    }
}
