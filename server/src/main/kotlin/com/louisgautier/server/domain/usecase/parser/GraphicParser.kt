package com.louisgautier.server.domain.usecase.parser

import kotlinx.serialization.Serializable

@Serializable
data class GraphicParser(
    val character: Char,
    val strokes: List<String>,
    val medians: List<List<List<Float>>>
)