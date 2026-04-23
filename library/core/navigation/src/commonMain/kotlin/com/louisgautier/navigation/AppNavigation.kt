package com.louisgautier.navigation

import androidx.navigation3.runtime.NavKey
import com.louisgautier.logger.AppLogger
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

object AppNavigation {
    private val _navigationEvents = Channel<NavigationCommand>(
        capacity = Channel.BUFFERED,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val navigationEvents: Flow<NavigationCommand> = _navigationEvents.receiveAsFlow()

    fun navigate(route: NavKey, clearBackStack: Boolean = false) {
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
        _navigationEvents.trySend(command)
    }
}