package xyz.luko.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionResponse(
    val code: Int,
    val pinyin: String,
    val strokes: List<Stroke>,
    val references: List<Stroke>,
    val recognitionResult: String,
    val difficultyLevel: DifficultyLevel,
    val accuracy: Float,
)

@Serializable
data class StrokeComparisonResult(
    val overallAccuracy: Float, // 0-100
    val strokeAccuracies: List<Float>,
    val orderAccuracy: Float,
    val details: ComparisonDetails,
)

@Serializable
data class ComparisonDetails(
    val pathSimilarity: Float,
    val startPointAccuracy: Float,
    val endPointAccuracy: Float,
    val directionAccuracy: Float,
)
