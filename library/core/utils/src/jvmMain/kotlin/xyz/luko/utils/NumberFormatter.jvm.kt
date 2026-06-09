package xyz.luko.utils

import java.text.NumberFormat

actual fun Number.toFormattedString(): String =
    NumberFormat.getNumberInstance().format(this)
