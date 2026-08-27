package xyz.luko.learning.congratulation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.luko.domain.model.ReviewResult
import xyz.luko.domain.model.Session
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute

internal class EndOfSessionCoordinator {

    private var queue: ArrayDeque<AppRoute.Learning> = ArrayDeque()

    suspend fun prepareAndStart(reviewResult: ReviewResult?, session: Session) {

        queue = ArrayDeque(
            if (reviewResult == null) listOf(AppRoute.Learning.Congratulation(session))
            else
                buildList {
                    if (reviewResult.isStreakUpdated) {
                        add(AppRoute.Learning.StreakUp(reviewResult.newStreak))
                    }
                    if (reviewResult.hasLevelUp) {
                        add(AppRoute.Learning.LevelUp(reviewResult.levels))
                    }
                    add(AppRoute.Learning.Congratulation(session))
                }
        )
        withContext(Dispatchers.Main) {
            AppNavigation.navigate(queue.first(), clearBackStack = true)
        }
    }

    fun next() {
        queue.removeFirstOrNull()
        queue.firstOrNull()?.let {
            AppNavigation.navigate(it, clearBackStack = true)
        } ?: AppNavigation.navigateHome()
    }
}
