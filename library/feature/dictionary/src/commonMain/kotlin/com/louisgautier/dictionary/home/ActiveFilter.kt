package com.louisgautier.dictionary.home

import kotlinx.serialization.Serializable

@Serializable
internal data class ActiveFilter(
    val isCommonActivated: Boolean = true,
    val isFrequentActivated: Boolean = true,
    val isStandardActivated: Boolean = true,
)
