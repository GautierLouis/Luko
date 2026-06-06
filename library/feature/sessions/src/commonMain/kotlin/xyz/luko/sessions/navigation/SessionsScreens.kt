package xyz.luko.sessions.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import xyz.luko.navigation.AppRoute
import xyz.luko.sessions.SessionListScreen

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavKey>.sessionsScreens() {

    entry<AppRoute.SessionsRoute.SessionListRoute> { SessionListScreen(it) }

}
