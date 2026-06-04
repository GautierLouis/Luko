package xyz.luko.learning.congratulation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.Session
import xyz.luko.domain.repository.SessionRepository

internal class EndOfSessionViewModel(
    sessionRepository: SessionRepository,
) : ViewModel() {
    sealed class UIState {
        data object Loading : UIState()

        data class Success(
            val session: Session,
            val newStreak: Int,
            val screen: EndOfSessionScreens = EndOfSessionScreens.STATS
        ) : UIState()
    }

    private val _state = MutableStateFlow<UIState>(UIState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val stats = sessionRepository.getEndOfSessionStats()

            val screen = when {
                stats.newStreak > stats.oldStreak -> EndOfSessionScreens.STREAK
                else -> EndOfSessionScreens.STATS
            }

            _state.update {
                UIState.Success(
                    session = stats.lastSession,
                    newStreak = stats.newStreak,
                    screen = screen
                )
            }
        }
    }

    fun navigateToStats() {
        if (_state.value == UIState.Loading) return
        val curr = _state.value as UIState.Success
        _state.update { curr.copy(screen = EndOfSessionScreens.STATS) }
    }
}
