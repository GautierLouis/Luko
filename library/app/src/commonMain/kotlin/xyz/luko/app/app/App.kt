package xyz.luko.app.app

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.app.main.MainScaffold
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.LocalAnimatedContentScope
import xyz.luko.learning.navigation.learningScreens
import xyz.luko.sessions.navigation.sessionsScreens
import xyz.luko.tracking.Tracker
import xyz.luko.tracking.TrackingEvent
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute
import xyz.luko.ui.navigation.NavigationCommand

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun App() {
    val viewModel: AppViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val backStack = rememberNavBackStack(navigationConfiguration, AppRoute.MainRoute)
    val isSystemDark = isSystemInDarkTheme()
    val themeMode by remember { derivedStateOf { state.theme.toThemeMode(isSystemDark) } }
    val strategy = rememberListDetailSceneStrategy<NavKey>()

    LaunchedEffect(Unit) {
        AppNavigation.navigationEvents.collect { event ->
            withContext(Dispatchers.Main) {
                when (event) {
                    is NavigationCommand.Navigate -> {
                        Tracker.track(TrackingEvent.NavigateTo(event.route.toString()))

                        if (!event.clearBackStack) {
                            backStack.removeAll { it::class == event.route::class }
                        }

                        if (event.clearBackStack) {
                            backStack.clear()
                            backStack += AppRoute.MainRoute
                        }

                        if (event.route !is AppRoute.MainRoute) {
                            backStack += event.route
                        }
                    }

                    is NavigationCommand.NavigateUp -> {
                        backStack.removeLast()
                    }
                }
            }
        }

    }

    AppTheme(themeMode) {
        SharedTransitionLayout {
            NavDisplay(
                entryDecorators =
                    listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(), // scopes VM to back stack entry
                        NavEntryDecorator { entry ->
                            CompositionLocalProvider(
                                LocalAnimatedContentScope provides LocalNavAnimatedContentScope.current
                            ) { entry.Content() }
                        }
                    ),
                backStack = backStack,
                onBack = { backStack.removeLast() },
                sceneStrategy = strategy,
                entryProvider =
                    entryProvider {
                        entry<AppRoute.MainRoute> { MainScaffold() }
                        learningScreens()
                        sessionsScreens()
                    },
            )
        }
    }
}
