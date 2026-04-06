package com.louisgautier.app.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.louisgautier.app.main.MainScaffold
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.learning.routing.learningScreens
import com.louisgautier.navigation.AppRoute
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel: AppViewModel = koinViewModel()
    App(viewModel)
}

@Composable
private fun App(
    viewModel: AppViewModel = koinViewModel()
) {
    val backStack = rememberNavBackStack(navigationConfiguration, AppRoute.MainRoute())
    val state by viewModel.state.collectAsStateWithLifecycle()

    val isSystemDark = isSystemInDarkTheme()
    val themeMode = state.theme.toThemeMode(isSystemDark)

    AppTheme(themeMode) {
        LaunchedEffect(backStack) {
            viewModel.observeNavigation(backStack)
        }

        Scaffold(
            topBar = {
                if (!state.isProduction) {
                    FlavorComponent(state.flavor)
                }
            }
        ) { paddingValues ->
            NavDisplay(
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