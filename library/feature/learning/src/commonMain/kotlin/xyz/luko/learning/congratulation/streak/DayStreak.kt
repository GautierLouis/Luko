package xyz.luko.learning.congratulation.streak

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

internal data class DayStreak(
    val date: LocalDate,
    val hasSession: Boolean,
    val isToday: Boolean
)

internal val dayStreakPreview by lazy {
    List(7) {
        DayStreak(
            Clock.System.now().toLocalDateTime(TimeZone.UTC).date.plus(it, DateTimeUnit.DAY),
            it <= 3,
            it == 3
        )
    }
}
