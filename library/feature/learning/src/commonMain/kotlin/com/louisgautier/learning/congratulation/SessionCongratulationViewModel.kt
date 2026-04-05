package com.louisgautier.learning.congratulation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.repository.SessionRepository
import com.louisgautier.navigation.AppNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SessionCongratulationViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    data class UIState(
        val session: Session? = null
    )

    private val _state = MutableStateFlow<UIState>(UIState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            sessionRepository.getLastSessions(1).firstOrNull()
                ?.let { lastSession -> _state.update { it.copy(session = lastSession) } }
                ?: AppNavigation.navigateHome()
        }
    }
}