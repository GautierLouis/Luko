package com.louisgautier.dictionary.home

import kotlinx.serialization.Serializable

@Serializable
data class ActiveFilter(
    val isCommonActivated: Boolean = true,
    val isFrequentActivated: Boolean = true,
    val isStandardActivated: Boolean = true,
)