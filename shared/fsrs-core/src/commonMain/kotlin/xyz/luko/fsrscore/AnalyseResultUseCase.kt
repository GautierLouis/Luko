package xyz.luko.fsrscore

import xyz.luko.apicontracts.dto.AttemptSignal
import xyz.luko.fsrscore.internal.AccuracyUseCase
import xyz.luko.fsrscore.internal.FsrsUseCase
import xyz.luko.fsrscore.internal.GradeUseCase
import xyz.luko.fsrscore.model.AnalysisResult
import kotlin.time.Clock

class AnalyseResultUseCase {

    fun analyse(signal: AttemptSignal): AnalysisResult {

        //run stroke comparison
        val accuracyResult = AccuracyUseCase.calculate(
            reference = AccuracyUseCase.rawStrokeToComparableStroke(signal.rawReferenceMedians),
            userStroke = AccuracyUseCase.strokeToComparableStroke(signal.strokes),
        )

        //compute Gradle
        val grade = GradeUseCase.deriveGrade(
            signals = signal,
            stroke = accuracyResult
        )

        //then run fsrs
        val result = FsrsUseCase.compute(
            current = signal.fsrsState,
            grade = grade,
            elapsedDays = signal.fsrsState?.lastReviewedAt?.let { elapsedDaysUntilNow(it) } ?: 0.0
        )

        return AnalysisResult(
            accuracy = accuracyResult,
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
