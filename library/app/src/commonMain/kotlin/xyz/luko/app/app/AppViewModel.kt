package xyz.luko.app.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.SettingTheme
import xyz.luko.domain.repository.AppStartUseCase
import xyz.luko.domain.repository.UserRepository
import xyz.luko.firebase.FirebaseManager
import xyz.luko.firebase.RemoteConfigManager
import xyz.luko.tracking.Tracker
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.utils.AppConfig
import xyz.luko.utils.AppLogger
import xyz.luko.utils.Flavor

internal class AppViewModel(
    private val appStartViewModel: AppStartUseCase,
    private val firebaseManager: FirebaseManager,
    private val remoteConfigManager: RemoteConfigManager,
    userRepository: UserRepository,
    appConfig: AppConfig,
) : ViewModel() {
    data class UiState(
        val showFlavorBanner: Boolean,
        val flavor: Flavor,
        val theme: ThemeMode? = null,
    )

    val state: StateFlow<UiState>
        field = MutableStateFlow(UiState(!appConfig.isProduction, appConfig.flavor))

    init {
        AppLogger.init()

        viewModelScope.launch {
            appStartViewModel.initialize()
        }

        userRepository
            .observeTheme()
            .onEach { theme ->
                state.update {
                    it.copy(
                        theme = when (theme) {
                            SettingTheme.Default -> null
                            SettingTheme.Night -> ThemeMode.Night
                            SettingTheme.Day -> ThemeMode.Day
                        }
                    )
                }
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
}
