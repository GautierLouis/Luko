package xyz.luko.learning.congratulation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import xyz.luko.domain.model.Session
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.navigation.AppNavigation

internal class SessionCongratulationViewModel(
    sessionRepository: SessionRepository,
) : ViewModel() {
    sealed class UIState {
        data object Loading : UIState()

        data object Error : UIState()

        data class Success(
            val session: Session,
        ) : UIState()
    }

    private val _state = MutableStateFlow<UIState>(UIState.Loading)
    val state = _state.asStateFlow()

    init {
        sessionRepository
            .getLastSessions(1)
            .distinctUntilChanged()
            .onEach { lastSession ->
                val session = lastSession.firstOrNull()
                if (session != null) {
                    _state.update { UIState.Success(session) }
                } else {
                    _state.update { UIState.Error }
                    AppNavigation.navigateHome()
                }
            }.launchIn(viewModelScope)
    }
}
