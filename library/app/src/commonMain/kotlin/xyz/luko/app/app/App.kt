package xyz.luko.app.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.app.main.MainScaffold
import xyz.luko.designsystem.components.page.BaseScaffold
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.learning.routing.learningScreens
import xyz.luko.navigation.AppRoute

@Composable
fun App() {
    val viewModel: AppViewModel = koinViewModel()
    val backStack = rememberNavBackStack(navigationConfiguration, AppRoute.MainRoute())
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.observeNavigation(backStack)
    }
    val isSystemDark = isSystemInDarkTheme()
    val themeMode by remember {
        derivedStateOf { state.theme.toThemeMode(isSystemDark) }
    }

    AppTheme(themeMode) {
        BaseScaffold(
            topBar = {
                if (state.showFlavorBanner) {
                    FlavorComponent(state.flavor)
                }
            },
        ) { _ ->
            NavDisplay(
                entryDecorators =
                    listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(), // scopes VM to back stack entry
                    ),
                backStack = backStack,
                onBack = { backStack.removeLast() },
                entryProvider =
                    entryProvider {
                        entry<AppRoute.MainRoute> { MainScaffold() }
                        learningScreens()
                    },
            )
        }
    }
}
