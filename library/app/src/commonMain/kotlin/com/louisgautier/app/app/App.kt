package com.louisgautier.app.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.louisgautier.app.main.MainScaffold
import com.louisgautier.designsystem.components.page.BaseScaffold
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.learning.routing.learningScreens
import com.louisgautier.navigation.AppRoute
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel: AppViewModel = koinViewModel()
    val backStack = rememberNavBackStack(navigationConfiguration, AppRoute.MainRoute())
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(backStack) {
        viewModel.observeNavigation(backStack)
    }
    val isSystemDark = isSystemInDarkTheme()
    val themeMode = state.theme.toThemeMode(isSystemDark)

    AppTheme(themeMode) {
        BaseScaffold(
            topBar = {
                if (!state.isProduction) {
                    FlavorComponent(state.flavor)
                }
            }
        ) { _ ->
            NavDisplay(
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()// scopes VM to back stack entry
                ),
                backStack = backStack,
                onBack = { backStack.removeLast() },
                entryProvider = entryProvider {
                    entry<AppRoute.MainRoute> { MainScaffold() }
                    learningScreens()
                }
            )
        }
    }
}