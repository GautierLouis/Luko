package xyz.luko.utils

import java.text.NumberFormat

actual fun Number.toFormattedString(): String =
    NumberFormat.getNumberInstance().format(this)

actual fun Number.toPercentage(): String =
    NumberFormat.getPercentInstance().format(this.toDouble() / 100)
