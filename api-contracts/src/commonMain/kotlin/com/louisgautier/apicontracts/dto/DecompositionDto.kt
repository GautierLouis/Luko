package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class DecompositionDto(
    val symbolCode: Int,
    val glyphsCode: List<Int>
)