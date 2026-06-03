package xyz.luko.learning.congratulation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds

data class WeekStreak(
    val days: ImmutableList<StreakDay>,
    val weekOffset: Int = 0
)

data class StreakDay(
    val date: LocalDate,
    val isCompleted: Boolean,
) {
    val isToday: Boolean
        get() = date == Clock.System.todayIn(TimeZone.currentSystemDefault())
}

internal class StreakRefreshViewModel : ViewModel() {

    data class UIState(
        val showNew: Boolean = false,
        val showBtn: Boolean = false,
        val streak: WeekStreak = WeekStreak(emptyList<StreakDay>().toImmutableList())
    )

    private val currentDayIndex: Int
        get() {
            val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
            return today.dayOfWeek.ordinal
        }

    private val _state = MutableStateFlow<UIState>(UIState())
    val state = _state.asStateFlow()

    init {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val data = WeekStreak(
            listOf(
                StreakDay(
                    date = LocalDate(2026, 6, 1),
                    isCompleted = true
                ),
                StreakDay(
                    date = LocalDate(2026, 6, 2),
                    isCompleted = false
                ),
                StreakDay(
                    date = LocalDate(2026, 6, 3),
                    isCompleted = true
                ),
                StreakDay(
                    date = LocalDate(2026, 6, 4),
                    isCompleted = true
                ),
                StreakDay(
                    date = LocalDate(2026, 6, 5),
                    isCompleted = true
                ),
                StreakDay(
                    date = LocalDate(2026, 6, 6),
                    isCompleted = false
                ),
                StreakDay(
                    date = LocalDate(2026, 6, 7),
                    isCompleted = true
                ),
            ).toImmutableList()
        )

        _state.update {
            it.copy(streak = data)
        }

        viewModelScope.launch {
            delay(600.milliseconds)
            _state.update { it.copy(showNew = true) }
            delay(600.milliseconds)
            _state.update { it.copy(showBtn = true) }
        }
    }
}
