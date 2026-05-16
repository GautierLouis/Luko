package xyz.luko.learning.congratulation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.Session
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.navigation.AppNavigation

internal class SessionCongratulationViewModel(
    private val sessionRepository: SessionRepository,
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
        viewModelScope.launch {
            sessionRepository
                .getLastSessions(1)
                .firstOrNull()
                ?.let { s -> _state.update { UIState.Success(s) } }
                ?: run {
                    _state.update { UIState.Error }
                    AppNavigation.navigateHome()
                }
        }
    }
}
