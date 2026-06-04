package xyz.luko.designsystem.token.string

import androidx.compose.runtime.Immutable

@Immutable
data class Plurals(
    val single: String,
    val multiple: String,
) {
    fun get(value: Int) = when (value) {
        1 -> single
        else -> multiple
    }
}
