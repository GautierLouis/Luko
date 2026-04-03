package com.louisgautier.composeApp.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.louisgautier.composeApp.main.MainScreen
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.learning.CongratulationRoute
import com.louisgautier.learning.SessionRoute
import com.louisgautier.learning.StartSessionRoute
import com.louisgautier.learning.builder.SessionBuilderScreen
import com.louisgautier.learning.congratulation.SessionCongratulationScreen
import com.louisgautier.learning.session.SessionScreen
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun App(
    viewModel: AppViewModel = koinViewModel()
) {
    val backStack = rememberNavBackStack(savedStateConfiguration, MainRoute())
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
                    entry<MainRoute> { MainScreen() }
                    entry<SessionRoute> { SessionScreen() }
                    entry<StartSessionRoute> { SessionBuilderScreen() }
                    entry<CongratulationRoute> { SessionCongratulationScreen() }
                }
            )
        }
    }
}