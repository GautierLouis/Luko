package xyz.luko.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.Statistics
import xyz.luko.domain.repository.SessionRepository
import kotlin.time.Duration

internal class HomeViewModel(
    private val sessionRepository: SessionRepository,
) : ViewModel() {
    data class UIState(
        val lastSessions: List<Session> = emptyList(),
        val stats: Statistics =
            Statistics(
                totalScore = 0,
                averageTime = Duration.ZERO,
                averageDifficulty = null,
                currentDayStreak = 0,
                sessionCount = 0,
            ),
    )

    private val _state = MutableStateFlow(UIState())
    val state = _state.asStateFlow()

    init {
        merge(lastSessionFlow(), statisticsFlow()).launchIn(viewModelScope)
    }

    private fun lastSessionFlow() = sessionRepository
        .getLastSessions(2)
        .distinctUntilChanged()
        .onEach { lastSessions ->
            _state.update {
                it.copy(lastSessions = lastSessions)
            }
        }

    private fun statisticsFlow() = sessionRepository.getStatistics()
        .onEach { stats ->
            _state.update { it.copy(stats = stats) }
        }
}
