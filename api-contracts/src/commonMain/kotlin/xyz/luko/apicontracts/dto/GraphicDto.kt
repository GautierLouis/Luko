package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class GraphicDto(
    val code: Int,
    val strokes: List<String>,
    val medians: List<StrokeDto>,
    val smootherMedians: List<StrokeDto>
)

@Serializable
data class StrokeDto(
    val points: List<PointDto>,
)

@Serializable
sealed class PointDto {

    data class Straight(
        val x: Float,
        val y: Float
    ) : PointDto()

    data class Curved(
        val x: Float,
        val y: Float,
        val cp1x: Float,
        val cp1y: Float,
        val cp2x: Float,
        val cp2y: Float
    ) : PointDto()
}
