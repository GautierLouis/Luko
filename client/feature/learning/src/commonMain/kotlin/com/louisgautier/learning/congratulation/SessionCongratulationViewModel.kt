package com.louisgautier.learning.congratulation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.repository.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SessionCongratulationViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private var lastSession = MutableStateFlow<Session?>(null)
    val session: MutableStateFlow<Session?> = lastSession

    init {
        viewModelScope.launch {
            lastSession.value = sessionRepository.getLastSessions(1)
                .firstOrNull()
        }
    }
}