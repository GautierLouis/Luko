package xyz.luko.learning.congratulation

import xyz.luko.domain.repository.SessionRepository
import xyz.luko.learning.navigation.LearningInternalRoute

internal class EndOfSessionUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend fun getRoute(): LearningInternalRoute {
        val stats = sessionRepository.getEndOfSessionStats()

        //TODO Do better
        return when {
            stats.newStreak > stats.oldStreak -> LearningInternalRoute.StreakRoute(
                stats.newStreak,
                stats.lastSession
            )
            else -> LearningInternalRoute.CongratulationRoute(stats.lastSession)
        }
    }
}
