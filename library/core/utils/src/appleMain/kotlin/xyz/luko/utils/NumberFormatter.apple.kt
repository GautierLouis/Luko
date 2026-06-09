package xyz.luko.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle

actual fun Number.toFormattedString(): String =
    NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterDecimalStyle
        usesGroupingSeparator = true
    }.stringFromNumber(NSNumber(this.toDouble())) ?: this.toString()
