package com.louisgautier.composeApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.louisgautier.auth.AuthRepository
import com.louisgautier.firebase.FirebaseManager
import com.louisgautier.firebase.RemoteConfigManager
import com.louisgautier.firebase.event.Tracker
import com.louisgautier.logger.AppLogger
import com.louisgautier.utils.AppNavigation
import com.louisgautier.utils.NavigationCommand
import com.louisgautier.utils.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppViewModel(
    private val firebaseManager: FirebaseManager,
    private val authRepository: AuthRepository,
    private val remoteConfigManager: RemoteConfigManager,
) : ViewModel() {

    init {

        firebaseManager.initialize()

        viewModelScope.launch {
            authRepository.registerAnonymously()
        }

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
                                popUpTo(Route.HomeRoute) {
                                    inclusive = false
                                }
                            }
                        }

                        is NavigationCommand.NavigateUp -> navController.navigateUp()
                        is NavigationCommand.NavigateHome -> navController.navigate(Route.HomeRoute) {
                            popUpTo(Route.HomeRoute) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }
}