package com.louisgautier.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisgautier.domain.repository.SettingTheme
import com.louisgautier.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal sealed class ProfileScreenEvent {
    data class OnThemeChanged(val theme: SettingTheme) : ProfileScreenEvent()
}

internal class ProfileViewModel(
    private val userRepository: UserRepository
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