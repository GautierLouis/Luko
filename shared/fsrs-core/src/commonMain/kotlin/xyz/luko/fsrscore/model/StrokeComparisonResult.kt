package xyz.luko.fsrscore.model

import kotlinx.serialization.Serializable

@Serializable
data class StrokeComparisonResult(
    val overallAccuracy: Float,
    val strokeAccuracies: List<Float>,
    val orderAccuracy: Float,
    val strokeCountCorrect: Boolean,
    val strokeOrderCorrect: Boolean,
    val details: ComparisonDetails,
)

@Serializable
data class ComparisonDetails(
    val pathSimilarity: Float,
    val startPointAccuracy: Float,
    val endPointAccuracy: Float,
    val directionAccuracy: Float,
)
