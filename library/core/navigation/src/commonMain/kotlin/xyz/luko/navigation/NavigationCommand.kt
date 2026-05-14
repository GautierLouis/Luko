package xyz.luko.navigation

import androidx.navigation3.runtime.NavKey

sealed class NavigationCommand {
    data object NavigateUp : NavigationCommand()

    data class Navigate(
        val route: NavKey,
        val clearBackStack: Boolean,
    ) : NavigationCommand()
}
