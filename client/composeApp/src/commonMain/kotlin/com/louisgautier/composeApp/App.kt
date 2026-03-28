package com.louisgautier.composeApp

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.learning.builder.SessionBuilderScreen
import com.louisgautier.learning.congratulation.CongratulationScreen
import com.louisgautier.learning.session.SessionScreen
import com.louisgautier.navigation.BuilderKey
import com.louisgautier.navigation.CongratulationKey
import com.louisgautier.navigation.MainKey
import com.louisgautier.navigation.SessionKey
import com.louisgautier.navigation.savedStateConfiguration
import com.louisgautier.utils.AppConfig
import org.koin.compose.viewmodel.koinViewModel
import org.koin.mp.KoinPlatform

@Composable
fun App(
    viewModel: AppViewModel = koinViewModel()
) {
    val backStack = rememberNavBackStack(savedStateConfiguration, MainKey())

    val appConfig = remember { KoinPlatform.getKoin().get<AppConfig>() }

    AppTheme {

        LaunchedEffect(backStack) {
            viewModel.observeNavigation(backStack)
        }

        Scaffold(
            topBar = {
                if (!appConfig.isProduction) {
                    FlavorComponent(appConfig.flavor)
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
                            CongratulationScreen()
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