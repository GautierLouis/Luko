package xyz.luko.utils

import java.text.DateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.Instant

actual fun Instant.toAccessibilityDate(): String {
    val date = Date(toEpochMilliseconds())
    return DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(date)
}
