package xyz.luko.learning.congratulation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
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
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds

internal class StreakRefreshViewModel : ViewModel() {

    data class UIState(
        val showNew: Boolean = false,
        val showBtn: Boolean = false,
        val streakDays: ImmutableList<StreakDay> = persistentListOf(),
    )

    private val _state = MutableStateFlow<UIState>(UIState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val dates = getDates()
            _state.update { it.copy(streakDays = buildList(dates)) }
            delay(600.milliseconds)
            _state.update { it.copy(showNew = true) }
            delay(600.milliseconds)
            _state.update { it.copy(showBtn = true) }
        }
    }


    private fun buildList(
        completedDates: List<LocalDate>,
    ): ImmutableList<StreakDay> {

        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val monday = today.minus(today.dayOfWeek.ordinal, DateTimeUnit.DAY)

        val completedSet = completedDates.toSet()

        val animatedDate = completedDates.maxOrNull()

        val currentStreakDates = buildSet {

            var cursor = animatedDate ?: return@buildSet

            add(cursor)

            while (cursor.minus(1, DateTimeUnit.DAY) in completedSet) {
                cursor = cursor.minus(1, DateTimeUnit.DAY)
                add(cursor)
            }
        }

        val days = (0..6).map { offset ->

            val date = monday.plus(offset, DateTimeUnit.DAY)

            val completed = date in completedSet

            val prevCompleted = date.minus(1, DateTimeUnit.DAY) in completedSet

            val nextCompleted = date.plus(1, DateTimeUnit.DAY) in completedSet

            val segment = when {
                !completed -> SegmentPosition.None

                !prevCompleted && !nextCompleted -> when (date.dayOfWeek) {
                    DayOfWeek.MONDAY -> SegmentPosition.Last
                    DayOfWeek.SUNDAY -> SegmentPosition.First
                    else -> SegmentPosition.Single
                }

                !prevCompleted && nextCompleted -> SegmentPosition.First

                prevCompleted && !nextCompleted -> SegmentPosition.Last

                prevCompleted && nextCompleted -> SegmentPosition.Middle

                else -> SegmentPosition.None
            }

            StreakDay(
                date = date,
                segment = segment,
                shouldAnimate = animatedDate == date,
                currentStreak = date in currentStreakDates
            )
        }

        return days.toImmutableList()
    }

    private suspend fun getDates(): List<LocalDate> {
        return listOf(
            "2026-06-01",
            "2026-06-03",
            "2026-06-04",
            "2026-06-05",
            "2026-06-07",
        ).map { LocalDate.parse(it) }
    }
}
