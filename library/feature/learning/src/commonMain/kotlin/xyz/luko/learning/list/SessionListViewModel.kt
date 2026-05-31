package xyz.luko.learning.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.repository.SessionRepository

internal sealed class PaneNavigationEvent {
    data object NavigateBack : PaneNavigationEvent()
    data class NavigateToDetails(val sessionId: Long) : PaneNavigationEvent()
    data class NavigateToExtra(val responseCode: Int) : PaneNavigationEvent()
}

internal class SessionListViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    data class UiState(
        // List Pane
        val sessions: List<Session> = emptyList(),
        // Detail Pane
        val selectedSessionId: Long? = null,
        val responses: List<SessionResponse> = emptyList(),
        // Extra Pane
        val selectedResponse: SessionResponse? = null,
        val similarResponses: List<SessionResponse> = emptyList(),
    )

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    private val _navigationEvents =
        MutableSharedFlow<PaneNavigationEvent>(
            replay = 0,
            extraBufferCapacity = 8,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )
    val navigationEvents = _navigationEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            val sessions = sessionRepository.getLastSessions()

            _state.update {
                it.copy(sessions = sessions)
            }
        }
    }

    fun onSessionSelected(sessionId: Long) {
        viewModelScope.launch {
            val responses = sessionRepository.getResponses(sessionId)
            _state.update {
                it.copy(selectedSessionId = sessionId, responses = responses)
            }
            _navigationEvents.tryEmit(PaneNavigationEvent.NavigateToDetails(sessionId))
        }
    }

    fun onResponseSelected(code: Int) {
        val isDeselecting = _state.value.selectedResponse?.code == code

        _state.update { s ->
            s.copy(
                selectedResponse = s.responses
                    .find { it.code == code }
                    .takeIf { !isDeselecting }
            )
        }

        if (!isDeselecting) {
            viewModelScope.launch {
                val r = sessionRepository.getSimilarResponse(code)
                _state.update { it.copy(similarResponses = r) }
            }
        }

        _navigationEvents.tryEmit(
            if (isDeselecting) PaneNavigationEvent.NavigateBack
            else PaneNavigationEvent.NavigateToExtra(code)
        )
    }

    fun navigateBack() {
        _navigationEvents.tryEmit(PaneNavigationEvent.NavigateBack)
    }
}
