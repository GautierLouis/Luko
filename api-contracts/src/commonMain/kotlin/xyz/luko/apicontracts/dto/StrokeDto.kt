package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class StrokeDto(
    val points: List<PointDto>,
)
