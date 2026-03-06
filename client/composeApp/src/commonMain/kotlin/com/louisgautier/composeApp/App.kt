package com.louisgautier.composeApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.louisgautier.composeApp.home.HomeScreen
import com.louisgautier.dictionary.DictionaryScreen
import com.louisgautier.learning.builder.SessionBuilderScreen
import com.louisgautier.learning.congratulation.SessionCongratulationScreen
import com.louisgautier.learning.session.SessionScreen
import com.louisgautier.utils.AppConfig
import com.louisgautier.utils.Route
import org.koin.compose.viewmodel.koinViewModel
import org.koin.mp.KoinPlatform

@Composable
fun App(
    viewModel: AppViewModel = koinViewModel()
) {
    val navController = rememberNavController()

    MaterialTheme {

        LaunchedEffect(navController) {
            viewModel.observeNavigation(navController)
        }

        Box {
            NavHost(
                navController = navController,
                startDestination = Route.HomeRoute,
            ) {
                composable<Route.HomeRoute> { HomeScreen() }
                composable<Route.SessionBuilderRoute> { SessionBuilderScreen() }
                composable<Route.SessionRoute> { SessionScreen() }
                composable<Route.DictionaryListRoute> { DictionaryScreen() }
                composable<Route.SessionCongratulationScreenRoute> { SessionCongratulationScreen() }
                composable<Route.PracticeCharacterRoute> { }
            }
            FlavorComponent()
        }
    }
}

@Composable
fun FlavorComponent() {
    val appConfig = remember { KoinPlatform.getKoin().get<AppConfig>() }

    if (!appConfig.isProduction) {
        Text(
            text = appConfig.flavor.uppercase(),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .background(if (appConfig.flavor == "dev") Color.Red else Color.Blue)
        )
    }
}