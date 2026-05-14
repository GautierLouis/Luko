package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class GraphicDto(
    val code: Int,
    val strokes: List<String>,
    val medians: List<StrokeDto>,
)

@Serializable
data class StrokeDto(
    val points: List<PointDto>,
)

@Serializable
data class PointDto(
    val x: Float,
    val y: Float,
)
