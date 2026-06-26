package xyz.luko.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSNumberFormatterPercentStyle

actual fun Number.toFormattedString(): String =
    NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterDecimalStyle
        usesGroupingSeparator = true
    }.stringFromNumber(NSNumber(this.toDouble())) ?: this.toString()

actual fun Number.toPercentage(): String {
    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterPercentStyle
        maximumFractionDigits = 0u
    }
    return formatter.stringFromNumber(NSNumber(this.toDouble() / 100)) ?: "${this}%"
}
