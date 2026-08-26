package xyz.luko.sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    val state: StateFlow<UiState>
        field = MutableStateFlow(UiState())

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

            val additionalInfo = sessions.firstOrNull()?.let { s ->
                val responses = sessionRepository.getResponses(s.id)
                val similar = responses.firstOrNull()?.let { sr ->
                    sessionRepository.getSimilarResponse(sr.code)
                }
                Triple(s.id, responses, similar)
            }

            state.update {
                it.copy(
                    sessions = sessions,
                    selectedSessionId = additionalInfo?.first,
                    responses = additionalInfo?.second.orEmpty(),
                    selectedResponse = additionalInfo?.second?.firstOrNull(),
                    similarResponses = additionalInfo?.third.orEmpty()
                )
            }
        }
    }

    fun initView(
        sessionId: Long? = null,
    ) {
        when {
            state.value.sessions.isEmpty() -> {
                //Do nothing
            }

            sessionId != null -> {
                val index = state.value.sessions.indexOfFirst { it.id == sessionId }
                onSessionSelected(sessionId, index.takeIf { it >= 0 })
            }
        }
    }

    fun onSessionSelected(sessionId: Long, scrollPosition: Int? = null) {
        viewModelScope.launch {

            if (sessionId != state.value.selectedSessionId) {
                val responses = sessionRepository.getResponses(sessionId)
                state.update {
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
        val isDeselecting = state.value.selectedResponse?.code == code

        state.update { s ->
            s.copy(
                selectedResponse = s.responses
                    .find { it.code == code }
                    .takeIf { !isDeselecting }
            )
        }

        if (!isDeselecting) {
            viewModelScope.launch {
                val r = sessionRepository.getSimilarResponse(code)
                state.update { it.copy(similarResponses = r) }
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
