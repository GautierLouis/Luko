package xyz.luko.sessions

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

            val firstResponses = sessions.firstOrNull()?.id?.let {
                it to sessionRepository.getResponses(it)
            }

            _state.update {
                it.copy(
                    sessions = sessions,
                    selectedSessionId = firstResponses?.first,
                    responses = firstResponses?.second.orEmpty()
                )
            }
        }
    }

    fun initView(
        sessionId: Long? = null,
        canShowDetail: Boolean,
    ) {
        when {
            _state.value.sessions.isEmpty() -> {
                //Do nothing
            }

            sessionId != null -> {
                val index = _state.value.sessions.indexOfFirst { it.id == sessionId }
                onSessionSelected(sessionId, index.takeIf { it >= 0 })
            }

            canShowDetail -> {
                onSessionSelected(_state.value.sessions.first().id, null)
            }
        }
    }

    fun onSessionSelected(sessionId: Long, scrollPosition: Int? = null) {
        viewModelScope.launch {

            if (sessionId != _state.value.selectedSessionId) {
                val responses = sessionRepository.getResponses(sessionId)
                _state.update {
                    it.copy(selectedSessionId = sessionId, responses = responses)
                }
            }

            _navigationEvents.tryEmit(
                PaneNavigationEvent.NavigateToDetails(
                    sessionId,
                    scrollPosition
                )
            )
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
