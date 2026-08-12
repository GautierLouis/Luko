package xyz.luko.server.domain.usecase

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import xyz.luko.apicontracts.dto.ReviewAttemptRequest
import xyz.luko.apicontracts.dto.ReviewResultDto
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

        val signals = progressionRepository.getSignals(id, attemptRequest)

        val progressions = signals.map {
            val analyseResult = analyseResultUseCase.analyse(it)
            val levels =
                levelUseCase.compute(analyseResult.fsrsResult.nextStability, it.fsrsState?.level)

            ProgressionRow(
                code = it.characterCode,
                stability = analyseResult.fsrsResult.nextStability,
                difficulty = analyseResult.fsrsResult.nextDifficulty,
                level = levels.first,
                levelUp = levels.second,
                nextReviewDueAt = dueDateFromNow(analyseResult.fsrsResult.nextIntervalDays).epochSeconds
            )
        }

        progressionRepository.saveProgression(
            id = id,
            progress = progressions,
            doneAt = attemptRequest.doneAt
        )

        val (isUpdated, newStreak) = streakUseCase.updateStreak(id)

        val levelsMaps = progressions
            .filter { it.levelUp }
            .groupBy { it.level }
            .map { it.key to it.value.map { p -> p.code } }
            .toMap()

        return ReviewResultDto(
            isStreakUpdated = isUpdated,
            newStreak = newStreak,
            hasLevelUp = progressions.any { it.levelUp },
            levels = levelsMaps
        )
    }
}
