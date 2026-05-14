package xyz.luko.app.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.luko.auth.AuthRepository
import xyz.luko.domain.repository.SettingTheme
import xyz.luko.domain.repository.UserRepository
import xyz.luko.firebase.FirebaseManager
import xyz.luko.firebase.RemoteConfigManager
import xyz.luko.navigation.AppNavigation
import xyz.luko.navigation.AppRoute
import xyz.luko.navigation.NavigationCommand
import xyz.luko.tracking.Tracker
import xyz.luko.tracking.TrackingEvent
import xyz.luko.utils.AppConfig
import xyz.luko.utils.Flavor

internal class AppViewModel(
    private val firebaseManager: FirebaseManager,
    private val authRepository: AuthRepository,
    private val remoteConfigManager: RemoteConfigManager,
    userRepository: UserRepository,
    appConfig: AppConfig,
) : ViewModel() {
    data class UiState(
        val showFlavorBanner: Boolean,
        val flavor: Flavor,
        val theme: SettingTheme = SettingTheme.Default,
    )

    private val _state = MutableStateFlow(UiState(!appConfig.isProduction, appConfig.flavor))
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
