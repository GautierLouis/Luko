package com.louisgautier.app.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.louisgautier.auth.AuthRepository
import com.louisgautier.domain.repository.SettingTheme
import com.louisgautier.domain.repository.UserRepository
import com.louisgautier.firebase.FirebaseManager
import com.louisgautier.firebase.RemoteConfigManager
import com.louisgautier.navigation.AppNavigation
import com.louisgautier.navigation.AppRoute
import com.louisgautier.navigation.NavigationCommand
import com.louisgautier.tracking.Tracker
import com.louisgautier.tracking.TrackingEvent
import com.louisgautier.utils.AppConfig
import com.louisgautier.utils.Flavor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class AppViewModel(
    private val firebaseManager: FirebaseManager,
    private val authRepository: AuthRepository,
    private val remoteConfigManager: RemoteConfigManager,
    userRepository: UserRepository,
    appConfig: AppConfig,
) : ViewModel() {
    data class UiState(
        val isProduction: Boolean,
        val flavor: Flavor,
        val theme: SettingTheme = SettingTheme.Default,
    )

    private val _state = MutableStateFlow(UiState(appConfig.isProduction, appConfig.flavor))
    val state = _state.asStateFlow()

    init {

        firebaseManager.initialize()

        viewModelScope.launch {
            authRepository.registerAnonymously()
        }

        userRepository
            .observeTheme()
            .onEach { theme ->
                _state.update { it.copy(theme = theme) }
            }.launchIn(viewModelScope)

        viewModelScope.launch {
            remoteConfigManager.completed.filter { it }.collect {}
        }

        viewModelScope.launch {
            Tracker.events.collect { event ->
                firebaseManager.logEvent(event)
            }
        }
    }

    fun observeNavigation(stack: NavBackStack<NavKey>) {
        viewModelScope.launch {
            AppNavigation.navigationEvents.collect { event ->
                withContext(Dispatchers.Main) {
                    when (event) {
                        is NavigationCommand.Navigate -> {
                            Tracker.track(TrackingEvent.NavigateTo(event.route.toString()))

                            if (event.clearBackStack) {
                                stack.clear()
                                stack += AppRoute.MainRoute()
                            }

                            if (event.route !is AppRoute.MainRoute) {
                                stack += event.route
                            }
                        }

                        is NavigationCommand.NavigateUp -> {
                            stack.removeLast()
                        }
                    }
                }
            }
        }
    }
}
