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
import kotlinx.coroutines.launch
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.Statistics
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.domain.repository.UserRepository
import kotlin.time.Duration

internal class HomeViewModel(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    data class UIState(
        val lastSession: Session? = null,
        val seeAll: Boolean = false,
        val stats: Statistics =
            Statistics(
                averageTime = Duration.ZERO,
                averageDifficulty = null,
                currentDayStreak = 0,
                sessionCount = 0,
                averageAccuracy = 0f,
                averageQuestionsCount = 0f
            ),
        val showOBStats: Boolean = false
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
                it.copy(
                    lastSession = lastSessions.firstOrNull(),
                    seeAll = lastSessions.isNotEmpty()
                )
            }
        }

    private fun statisticsFlow() = sessionRepository.getStatistics()
        .onEach { stats ->
            _state.update { it.copy(stats = stats) }
        }

    fun event(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.StartOnboarding -> startOnboarding()
        }
    }

    fun startOnboarding() {
        viewModelScope.launch {
            userRepository.setOnboarding(true)
        }
    }
}
