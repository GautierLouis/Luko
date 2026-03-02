package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class EtymologyDto(
    val type: EtymologyTypeDto? = null,
    val phonetic: String? = null,
    val semantic: String? = null,
    val hint: String? = null,
)