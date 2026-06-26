package xyz.luko.learning.congratulation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.learning.navigation.LearningInternalRoute
import xyz.luko.ui.navigation.AppNavigation

internal class EndOfSessionCoordinator(
    private val sessionRepository: SessionRepository,
) {

    private var queue: ArrayDeque<LearningInternalRoute> = ArrayDeque()

    suspend fun prepareAndStart() {
        val stats = sessionRepository.getEndOfSessionStats()
        queue = ArrayDeque(buildList {
            if (stats.newStreak > stats.oldStreak) {
                add(LearningInternalRoute.StreakRoute(stats.newStreak, stats.lastSession))
            }
            add(
                LearningInternalRoute.CongratulationRoute(
                    stats.lastSession,
                    stats.overhaulAccuracy
                )
            )
        })
        withContext(Dispatchers.Main) {
            AppNavigation.navigate(queue.first())
        }
    }

    fun next() {
        val route = queue.removeFirstOrNull() ?: return
        AppNavigation.navigate(route)
    }
}
