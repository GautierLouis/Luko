package xyz.luko.app.app

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
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
import xyz.luko.app.debug.DebugMenuScreen
import xyz.luko.app.main.MainScaffold
import xyz.luko.dictionary.navigation.BottomSheetSceneStrategy
import xyz.luko.dictionary.navigation.dictionaryRoutes
import xyz.luko.home.strings.learningStringProvider
import xyz.luko.learning.learningRoutes
import xyz.luko.onboarding.onboardingRoutes
import xyz.luko.onboarding.strings.onboardingStringProvider
import xyz.luko.sessions.sessionsRoutes
import xyz.luko.tracking.Tracker
import xyz.luko.tracking.TrackingEvent
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.LocalAnimatedContentScope
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute
import xyz.luko.ui.navigation.NavigationCommand
import xyz.luko.ui.navigation.savedStateConfiguration

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val viewModel: AppViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val featureStrings = remember {
        listOf(
            onboardingStringProvider,
            learningStringProvider
        )
    }

    val backStack = rememberNavBackStack(savedStateConfiguration, AppRoute.Home.Main)
    val strategy = rememberListDetailSceneStrategy<NavKey>()
    val bottomSheetStrategy = remember { BottomSheetSceneStrategy<NavKey>() }

    LaunchedEffect(Unit) {
        AppNavigation.navigationEvents.collect { event ->
            withContext(Dispatchers.Main) {
                when (event) {
                    is NavigationCommand.Navigate -> {
                        Tracker.track(TrackingEvent.NavigateTo(event.route.toString()))

                        if (event.clearBackStack) {
                            backStack.clear()
                            backStack += AppRoute.Home.Main
                        } else {
                            backStack.removeAll { it::class == event.route::class }
                        }

                        if (event.route !is AppRoute.Home.Main || backStack.isEmpty()) {
                            backStack += event.route
                        }
                    }

                    is NavigationCommand.NavigateUp -> {
                        if (backStack.size > 1) {
                            backStack.removeLast()
                        }
                    }
                }
            }
        }
    }

    AppTheme(
        themeMode = state.theme,
        featureStrings = featureStrings
    ) {
        NavDisplay(
            sceneStrategies = listOf(strategy, bottomSheetStrategy),
            entryDecorators =
                listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator(), // scopes VM to back stack entry
                    NavEntryDecorator { entry ->
                        CompositionLocalProvider(
                            LocalAnimatedContentScope provides LocalNavAnimatedContentScope.current
                        ) { entry.Content() }
                    },
                ),
            backStack = backStack,
            onBack = { backStack.removeLast() },
            entryProvider =
                entryProvider {
                    entry<AppRoute.Home.Main> { MainScaffold() }
                    entry<AppRoute.Home.DebugMenu> { DebugMenuScreen() }
                    learningRoutes()
                    onboardingRoutes()
                    sessionsRoutes()
                    dictionaryRoutes()
                },
        )
    }
}
