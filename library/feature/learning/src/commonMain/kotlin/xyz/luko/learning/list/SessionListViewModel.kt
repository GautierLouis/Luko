package xyz.luko.learning.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.Session
import xyz.luko.domain.repository.SessionRepository

internal class SessionListViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    data class UiState(
        val sessions: List<Session> = emptyList()
    )

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            sessionRepository.getLastSessions()
                .let { list ->
                    _state.update { it.copy(sessions = list) }
                }
        }
    }
}
