package com.louisgautier.dictionary

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
sealed interface DictionaryRoute : NavKey

@Serializable
data class PracticeRoute(val characterCode: Int) : DictionaryRoute