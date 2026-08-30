package xyz.luko.server.domain.usecase

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import xyz.luko.apicontracts.dto.ComparisonDetailsDto
import xyz.luko.apicontracts.dto.ReviewAttemptRequest
import xyz.luko.apicontracts.dto.ReviewResultDto
import xyz.luko.apicontracts.dto.StrokeComparisonResultDto
import xyz.luko.fsrscore.AnalyseResultUseCase
import xyz.luko.server.domain.mapper.dueDateFromNow
import xyz.luko.server.domain.model.ProgressionRow
import xyz.luko.server.domain.repo.ProgressionRepository

class ReviewUseCase(
    private val progressionRepository: ProgressionRepository,
    private val analyseResultUseCase: AnalyseResultUseCase,
    private val levelUseCase: LevelUseCase,
    private val streakUseCase: StreakUseCase,
) {

    suspend fun reviewSession(
        id: EntityID<Int>,
        attemptRequest: ReviewAttemptRequest,
    ): ReviewResultDto {

        // build signals from response (in batch)
        val signals = progressionRepository.getSignals(id, attemptRequest.session.responses)

        //For each response, analyze result (stroke comparison, fsrs, grade), return a progression
        val progressions = signals.map {

            val analyseResult = analyseResultUseCase.analyse(it)
            val levels = levelUseCase.compute(
                stability = analyseResult.fsrsResult.nextStability,
                currentLevel = it.fsrsState?.level
            )

            ProgressionRow(
                code = it.characterCode,
                stability = analyseResult.fsrsResult.nextStability,
                difficulty = analyseResult.fsrsResult.nextDifficulty,
                level = levels.first,
                levelUp = levels.second,
                nextReviewDueAt = dueDateFromNow(analyseResult.fsrsResult.nextIntervalDays).epochSeconds,
                strokeComparison = StrokeComparisonResultDto(
                    overallAccuracy = analyseResult.strokeComparison.overallAccuracy,
                    strokeAccuracies = analyseResult.strokeComparison.strokeAccuracies,
                    orderAccuracy = analyseResult.strokeComparison.orderAccuracy,
                    details = ComparisonDetailsDto(
                        pathSimilarity = analyseResult.strokeComparison.details.pathSimilarity,
                        startPointAccuracy = analyseResult.strokeComparison.details.startPointAccuracy,
                        endPointAccuracy = analyseResult.strokeComparison.details.endPointAccuracy,
                        directionAccuracy = analyseResult.strokeComparison.details.directionAccuracy,
                    )
                )
            )
        }

        // Calculate if streak should be increased, if yes, return the new streak
        val streakUpdater = streakUseCase.updateStreak(id)

        // Save user progression (streak, Fsrs, levels, session)
        progressionRepository.saveProgression(
            id = id,
            attemptRequest = attemptRequest,
            progressions = progressions,
            streakUpdater = streakUpdater
        )

        // Map that contains only character that leveled up during this session
        val levelsMaps = progressions
            .filter { it.levelUp }
            .groupBy { it.level }
            .map { it.key to it.value.map { p -> p.code } }
            .toMap()

        return ReviewResultDto(
            isStreakUpdated = streakUpdater.hasIncrease,
            newStreak = streakUpdater.newStreak,
            hasLevelUp = progressions.any { it.levelUp },
            levels = levelsMaps,
            strokeComparison = progressions.associate { it.code to it.strokeComparison }
        )
    }
}

