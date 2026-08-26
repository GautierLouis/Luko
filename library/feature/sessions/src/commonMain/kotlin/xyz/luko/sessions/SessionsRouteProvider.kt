package xyz.luko.sessions

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import xyz.luko.ui.navigation.AppRoute

fun EntryProviderScope<NavKey>.sessionsRoutes() {
    entry<AppRoute.Sessions.List> { SessionListScreen(it) }
}

