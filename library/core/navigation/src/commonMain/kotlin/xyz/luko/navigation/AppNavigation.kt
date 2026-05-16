package xyz.luko.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import xyz.luko.logger.AppLogger

object AppNavigation {
    private val _navigationEvents =
        MutableSharedFlow<NavigationCommand>(
            replay = 0,
            extraBufferCapacity = 8,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun navigate(
        route: NavKey,
        clearBackStack: Boolean = false,
    ) {
        navigate(NavigationCommand.Navigate(route, clearBackStack))
    }

    fun navigateUp() {
        navigate(NavigationCommand.NavigateUp)
    }

    fun navigateHome() {
        navigate(NavigationCommand.Navigate(AppRoute.MainRoute(), true))
    }

    private fun navigate(command: NavigationCommand) {
        AppLogger.d(tag = "Navigation event", message = command.toString())
        _navigationEvents.tryEmit(command)
    }
}
