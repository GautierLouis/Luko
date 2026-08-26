package xyz.luko.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.SettingTheme
import xyz.luko.domain.repository.UserRepository

internal sealed class ProfileScreenEvent {
    data class OnThemeChanged(
        val theme: SettingTheme,
    ) : ProfileScreenEvent()
}

internal class ProfileViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    internal data class UiState(
        val selectedTheme: SettingTheme? = null,
    )

    val state: StateFlow<UiState>
        field = MutableStateFlow(UiState())

    init {

        viewModelScope.launch {
            val theme = userRepository.getTheme()
            state.update { it.copy(selectedTheme = theme) }
        }
    }

    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.OnThemeChanged -> setUserTheme(event.theme)
        }
    }

    private fun setUserTheme(theme: SettingTheme) {
        state.update { it.copy(selectedTheme = theme) }
        viewModelScope.launch {
            userRepository.setTheme(theme)
        }
    }
}
