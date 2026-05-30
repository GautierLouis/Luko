package xyz.luko.utils

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterLongStyle
import platform.Foundation.NSDateFormatterNoStyle
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970

actual fun kotlin.time.Instant.toAccessibilityDate(): String {
    val formatter = NSDateFormatter().apply {
        dateStyle = NSDateFormatterLongStyle
        timeStyle = NSDateFormatterNoStyle
        locale = NSLocale.currentLocale
    }
    val nsDate = NSDate.dateWithTimeIntervalSince1970(toEpochMilliseconds() / 1000.0)
    return formatter.stringFromDate(nsDate)
}
