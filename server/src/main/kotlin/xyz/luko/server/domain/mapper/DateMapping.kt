package xyz.luko.server.domain.mapper

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

/**
 * Calculates a due date starting from the current point in time.
 */
fun dueDateFromNow(dayInterval: Int): Instant {
    return Clock.System.now()
        .plus(dayInterval, DateTimeUnit.DAY, TimeZone.UTC)
        .toLocalDateTime(TimeZone.UTC).date.atStartOfDayIn(TimeZone.UTC)
}
