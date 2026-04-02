package com.louisgautier.composeApp.app

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.louisgautier.composeApp.main.MainScreen
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.learning.builder.SessionBuilderScreen
import com.louisgautier.learning.congratulation.SessionCongratulationScreen
import com.louisgautier.learning.session.SessionScreen
import com.louisgautier.navigation.BuilderKey
import com.louisgautier.navigation.CongratulationKey
import com.louisgautier.navigation.MainKey
import com.louisgautier.navigation.SessionKey
import com.louisgautier.navigation.savedStateConfiguration
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    viewModel: AppViewModel = koinViewModel()
) {
    val backStack = rememberNavBackStack(savedStateConfiguration, MainKey())
    val state by viewModel.state.collectAsStateWithLifecycle()

    AppTheme {
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
                entryProvider = { key ->
                    when (key) {
                        is MainKey -> NavEntry(key) {
                            MainScreen()
                        }

                        is BuilderKey -> NavEntry(key) {
                            SessionBuilderScreen()
                        }

                        is SessionKey -> NavEntry(key) {
                            SessionScreen()
                        }

                        is CongratulationKey -> NavEntry(key) {
                            SessionCongratulationScreen()
                        }

                        else -> {
                            error("Unknown route: $key")
                        }
                    }
                }
            )
        }
    }
}