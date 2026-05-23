package xyz.luko.server.domain.model.source

import kotlinx.serialization.Serializable

@Serializable
data class GraphicSource(
    val character: Char,
    val strokes: List<String>,
    val medians: List<List<List<Float>>>
)
