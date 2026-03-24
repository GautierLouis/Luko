package com.louisgautier.designsystem

import androidx.compose.runtime.Immutable

enum class StringsLocale {
    EN, FR,
}

@Immutable
data class Strings(
    val greeting: String
)

val stringsFR: Strings = Strings(
    greeting = "Bonjour"
)
val stringsEN: Strings = Strings(
    greeting = "Hello"
)