package xyz.luko.learning.congratulation.streak.ui

import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

internal data class StreakDay(
    val date: LocalDate,
    val segment: SegmentPosition = SegmentPosition.None,
    val shouldAnimate: Boolean = false,
    val currentStreak: Boolean = false,
) {
    val isMonday = date.dayOfWeek == DayOfWeek.MONDAY
    val isSunday = date.dayOfWeek == DayOfWeek.SUNDAY
}

internal enum class SegmentPosition {
    First, Middle, Last, None, Single
}

internal val previewStreakDays = listOf(
    StreakDay(
        date = LocalDate(2026, 6, 1),
        segment = SegmentPosition.Last,
        shouldAnimate = true
    ),
    StreakDay(
        date = LocalDate(2026, 6, 2),
        segment = SegmentPosition.None
    ),
    StreakDay(
        date = LocalDate(2026, 6, 3),
    ),
    StreakDay(
        date = LocalDate(2026, 6, 4),
    ),
    StreakDay(
        date = LocalDate(2026, 6, 5),
    ),
    StreakDay(
        date = LocalDate(2026, 6, 6),
    ),
    StreakDay(
        date = LocalDate(2026, 6, 7),
    ),
).toImmutableList()


