package com.louisgautier.navigation


sealed class NavigationCommand {
    data object NavigateUp : NavigationCommand()
    data class Navigate(val route: Route, val clearBackStack: Boolean) : NavigationCommand()
    data object NavigateHome : NavigationCommand()
}