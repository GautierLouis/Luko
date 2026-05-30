package xyz.luko.learning.session_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SessionResponse
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.navigation.AppRoute

internal class SessionDetailsViewModel(
    route: AppRoute.LearningRoute.SessionDetailsRoute,
    private val sessionRepository: SessionRepository,
) : ViewModel() {

    sealed class UiState {
        data class Success(
            val session: Session,
            val response: List<SessionResponse>
        ) : UiState()

        object Loading : UiState()
    }

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            coroutineScope {
                val session = async { sessionRepository.getSession(route.id) }
                val responses = async { sessionRepository.getResponses(route.id) }
                _state.update { UiState.Success(session.await(), responses.await()) }
            }
        }
    }
}
