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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.louisgautier.composeApp.home.HomeScreen
import com.louisgautier.dictionary.DictionaryScreen
import com.louisgautier.firebase.FirebaseManager
import com.louisgautier.firebase.RemoteConfigManager
import com.louisgautier.firebase.event.Tracker
import com.louisgautier.learning.builder.SessionBuilderScreen
import com.louisgautier.learning.congratulation.SessionCongratulationScreen
import com.louisgautier.learning.session.SessionScreen
import com.louisgautier.logger.AppLogger
import com.louisgautier.utils.AppConfig
import com.louisgautier.utils.AppNavigation
import com.louisgautier.utils.NavigationCommand
import com.louisgautier.utils.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.mp.KoinPlatform

class AppViewModel(
    private val firebaseManager: FirebaseManager,
    private val remoteConfigManager: RemoteConfigManager,
) : ViewModel() {

    init {

        firebaseManager.initialize()

        viewModelScope.launch {
            remoteConfigManager.events.collect {

            }
        }

        viewModelScope.launch {
            Tracker.events.collect { event ->
                AppLogger.d(tag = "Tracking event", message = event.toString())
                firebaseManager.logEvent(event)
            }
        }
    }

    fun observeNavigation(navController: NavController) {
        viewModelScope.launch {
            AppNavigation.navigationEvents.collect { event ->
                AppLogger.d(tag = "Navigation event", message = event.toString())
                withContext(Dispatchers.Main) {
                    when (event) {
                        is NavigationCommand.Navigate -> navController.navigate(event.route) {
                            if (event.clearBackStack) {
                                popUpTo(Route.HomeRoute) { inclusive = false }
                            }
                        }

                        is NavigationCommand.NavigateUp -> navController.navigateUp()
                        is NavigationCommand.NavigateHome -> navController.navigate(Route.HomeRoute) {
                            popUpTo(Route.HomeRoute) { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}

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