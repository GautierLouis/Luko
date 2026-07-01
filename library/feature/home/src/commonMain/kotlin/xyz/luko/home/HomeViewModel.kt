package xyz.luko.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Session
import xyz.luko.domain.repository.SessionRepository
import xyz.luko.domain.repository.UserRepository

internal class HomeViewModel(
    sessionRepository: SessionRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    data class UIState(
        val lastSession: Session? = null,
        val seeAll: Boolean = false,
        val streakCount: Int = 0,
        val sessionCount: Int = 0,
        val avgDifficultyLevel: DifficultyLevel? = null,
        val avgAccuracy: Double = 0.0
    )

    private val _state = MutableStateFlow(UIState())
    val state = _state.asStateFlow()

    init {
        combine(
            sessionRepository.getLastSessions(2),
            sessionRepository.getStatistics(),
            userRepository.observeStreak()
        ) { lastSessions, statistics, streak ->
            UIState(
                lastSession = lastSessions.firstOrNull(),
                seeAll = lastSessions.isNotEmpty(),
                streakCount = streak.streakCount,
                sessionCount = statistics.sessionCount,
                avgDifficultyLevel = statistics.averageDifficulty,
                avgAccuracy = statistics.averageAccuracy
            )
        }
            .onEach { new -> _state.update { new } }
            .launchIn(viewModelScope)
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
