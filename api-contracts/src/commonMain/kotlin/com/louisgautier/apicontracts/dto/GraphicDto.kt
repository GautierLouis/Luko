package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class GraphicDto(
    val code: Int,
    val strokes: List<String>,
    val medians: List<List<List<Float>>>
)