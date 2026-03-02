package com.louisgautier.domain.model

data class Etymology(
    val type: EtymologyType? = null,
    val phonetic: String? = null,
    val semantic: String? = null,
    val hint: String? = null,
)