package com.louisgautier.composeApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.louisgautier.auth.AuthRepository
import com.louisgautier.firebase.FirebaseManager
import com.louisgautier.firebase.RemoteConfigManager
import com.louisgautier.firebase.event.Tracker
import com.louisgautier.logger.AppLogger
import com.louisgautier.navigation.AppNavigation
import com.louisgautier.navigation.MainKey
import com.louisgautier.navigation.NavigationCommand
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

    fun observeNavigation(stack: NavBackStack<NavKey>) {
        viewModelScope.launch {
            AppNavigation.navigationEvents.collect { event ->
                AppLogger.d(tag = "Navigation event", message = event.toString())
                withContext(Dispatchers.Main) {
                    when (event) {
                        is NavigationCommand.Navigate -> {
                            if (event.clearBackStack) {
                                stack.clear()
                            }
                            stack += event.route
                        }

                        is NavigationCommand.NavigateUp -> {
                            stack.removeLast()
                        }
                        is NavigationCommand.NavigateHome -> {
                            stack.clear()
                            stack += MainKey()
                        }
                    }
                }
            }
        }
    }
}