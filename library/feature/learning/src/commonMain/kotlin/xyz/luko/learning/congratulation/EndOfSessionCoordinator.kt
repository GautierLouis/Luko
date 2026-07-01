package xyz.luko.learning.congratulation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.domain.repository.UpdateStreakUseCase
import xyz.luko.learning.navigation.LearningInternalRoute
import xyz.luko.ui.navigation.AppNavigation

internal class EndOfSessionCoordinator(
    private val updateStreakUseCase: UpdateStreakUseCase,
    private val sessionRepository: SessionRepository,
) {

    private var queue: ArrayDeque<LearningInternalRoute> = ArrayDeque()

    suspend fun prepareAndStart() {
        val streakResult = updateStreakUseCase.maybeUpdate()

        val lastSession = sessionRepository.getLastSession()

        queue = ArrayDeque(buildList {
            if (!streakResult.updatedToday) {
                add(LearningInternalRoute.StreakRoute(streakResult.streakCount, lastSession))
            }
            add(LearningInternalRoute.CongratulationRoute(lastSession))
        })
        withContext(Dispatchers.Main) {
            AppNavigation.navigate(queue.first())
        }
    }

    fun next() {
        val route = queue.drop(1).firstOrNull() ?: return
        AppNavigation.navigate(route)
    }
}
