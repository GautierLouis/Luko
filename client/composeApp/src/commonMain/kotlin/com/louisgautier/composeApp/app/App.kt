package com.louisgautier.composeApp.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.louisgautier.composeApp.main.MainScreen
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.Reset
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
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
    val backStack = rememberNavBackStack(savedStateConfiguration, SplashRoute)
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
                    entry<SplashRoute> { SplashScreen() }
                    entry<MainRoute> { MainScreen() }
                    entry<SessionRoute> { SessionScreen() }
                    entry<StartSessionRoute> { SessionBuilderScreen() }
                    entry<CongratulationRoute> { SessionCongratulationScreen() }
                }
            )
        }
    }
}

//TODO(release) Change Icon !!
@Composable
fun SplashScreen() {
    Scaffold(
        containerColor = Theme.materialColors.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = AppIcon.Reset,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(240.dp)
                    .background(
                        color = Theme.materialColors.inverseSurface,
                        shape = CircleShape
                    )
                    .padding(16.dp)
            )
        }
    }
}