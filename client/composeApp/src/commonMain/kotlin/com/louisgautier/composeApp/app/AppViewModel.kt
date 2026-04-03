package com.louisgautier.composeApp.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.louisgautier.auth.AuthRepository
import com.louisgautier.domain.repository.SettingTheme
import com.louisgautier.domain.repository.UserRepository
import com.louisgautier.firebase.FirebaseManager
import com.louisgautier.firebase.RemoteConfigManager
import com.louisgautier.firebase.event.Tracker
import com.louisgautier.logger.AppLogger
import com.louisgautier.navigation.AppNavigation
import com.louisgautier.navigation.NavigationCommand
import com.louisgautier.utils.AppConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppViewModel(
    private val firebaseManager: FirebaseManager,
    private val authRepository: AuthRepository,
    private val remoteConfigManager: RemoteConfigManager,
    private val userRepository: UserRepository,
    appConfig: AppConfig
) : ViewModel() {

    data class UiState(
        val isProduction: Boolean,
        val flavor: String,
        val theme: SettingTheme = SettingTheme.Default
    )

    private val _state = MutableStateFlow(UiState(appConfig.isProduction, appConfig.flavor))
    val state = _state.asStateFlow()

    init {

        firebaseManager.initialize()

        viewModelScope.launch {
            authRepository.registerAnonymously()
        }

        userRepository.observeTheme().onEach { theme ->
            _state.update { it.copy(theme = theme) }
        }.launchIn(viewModelScope)

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
                                stack += MainRoute()
                            }
                            stack += event.route
                        }

                        is NavigationCommand.NavigateUp -> {
                            stack.removeLast()
                        }

                        is NavigationCommand.NavigateHome -> {
                            stack.clear()
                            stack += MainRoute()
                        }
                    }
                }
            }
        }
    }
}