package xyz.luko.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Stroke(
    val points: List<Point>,
)
