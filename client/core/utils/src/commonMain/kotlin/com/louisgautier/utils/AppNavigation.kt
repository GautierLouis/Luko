package com.louisgautier.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

sealed class NavigationCommand {
    data object NavigateUp : NavigationCommand()
    data class Navigate(val route: Route, val clearBackStack: Boolean) : NavigationCommand()
    data object NavigateHome : NavigationCommand()
}

object AppNavigation {
    private val _navigationEvents = Channel<NavigationCommand>(
        capacity = Channel.BUFFERED,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val navigationEvents: Flow<NavigationCommand> = _navigationEvents.receiveAsFlow()


    fun navigate(route: Route, clearBackStack: Boolean = false) {
        _navigationEvents.trySend(NavigationCommand.Navigate(route, clearBackStack))
    }

    fun navigateUp() {
        _navigationEvents.trySend(NavigationCommand.NavigateUp)
    }

    fun navigateHome() {
        _navigationEvents.trySend(NavigationCommand.NavigateHome)
    }
}