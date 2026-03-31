package com.louisgautier.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.math.abs
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
//TODO actual/expect
fun Instant.toISODateString(): String {
    val localDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.year}-${
        localDateTime.month.number.toString().padStart(2, '0')
    }-${localDateTime.day.toString().padStart(2, '0')}"
}

fun Duration.toHHMMSS(): String {
    val totalSeconds = this.inWholeSeconds
    val absSeconds = abs(totalSeconds)

    val hours = absSeconds / 3600
    val minutes = (absSeconds % 3600) / 60
    val seconds = absSeconds % 60


    return buildString {
        if (hours > 0) {
            append(hours)
            append(":")
        }
        append(minutes.toString().padStart(2, '0'))
        append(":")
        append(seconds.toString().padStart(2, '0'))
    }
}

@OptIn(ExperimentalTime::class)
fun String.toUTCDate() = Instant.parse(this).toLocalDateTime(TimeZone.UTC).date

@OptIn(ExperimentalTime::class)
fun String.toUTCDateTime() = Instant.parse(this).toLocalDateTime(TimeZone.UTC)

@OptIn(ExperimentalTime::class)
fun nowInUtc() = Clock.System.todayIn(TimeZone.UTC)