package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class StrokeComparisonResultDto(
    val overallAccuracy: Float,
    val strokeAccuracies: List<Float>,
    val orderAccuracy: Float,
    val details: ComparisonDetailsDto,
)

@Serializable
data class ComparisonDetailsDto(
    val pathSimilarity: Float,
    val startPointAccuracy: Float,
    val endPointAccuracy: Float,
    val directionAccuracy: Float,
)
