package com.louisgautier.server.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GraphicEntity(
    val code: Int,
    val strokes: String,
    val medians: String
)