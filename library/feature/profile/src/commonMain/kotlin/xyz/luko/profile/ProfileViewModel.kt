package xyz.luko.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.repository.SettingTheme
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

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {

        viewModelScope.launch {
            val theme = userRepository.getTheme()
            _state.update { it.copy(selectedTheme = theme) }
        }
    }

    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.OnThemeChanged -> setUserTheme(event.theme)
        }
    }

    private fun setUserTheme(theme: SettingTheme) {
        _state.update { it.copy(selectedTheme = theme) }
        viewModelScope.launch {
            userRepository.setTheme(theme)
        }
    }
}
