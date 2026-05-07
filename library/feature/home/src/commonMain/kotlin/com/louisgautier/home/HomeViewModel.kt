package com.louisgautier.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.model.Statistics
import com.louisgautier.domain.repository.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Instant

internal class HomeViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    data class UIState(
        val isLoading: Boolean = true,
        val lastSession: Session? = null,
        val stats: Statistics = Statistics(
            totalScore = 0,
            averageTime = Duration.ZERO,
            averageDifficulty = null,
            currentDayStreak = 0,
            sessionCount = 0
        ),
        val topbarTitle: GreetingMessage = GreetingMessage.GOOD_MORNING
    )

    private val _state = MutableStateFlow(UIState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val result = sessionRepository.getLastSessions(1)
                .firstOrNull()
            val stats = sessionRepository.getStatistics()
            _state.update {
                it.copy(
                    isLoading = false,
                    lastSession = result,
                    stats = stats,
                    topbarTitle = getGreeting(result?.date)
                )
            }
        }
    }

    fun getGreeting(date: Instant?): GreetingMessage {
        val hour = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .hour

        return if (date?.isToday() == true) {
            GreetingMessage.WELCOME_BACK
        } else when (hour) {
            in 0..11 -> GreetingMessage.GOOD_MORNING
            in 12..18 -> GreetingMessage.GOOD_AFTERNOON
            else -> GreetingMessage.GOOD_EVENING
        }
    }

    private fun Instant.isToday(): Boolean {
        val other = this.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        return other == today
    }
}

