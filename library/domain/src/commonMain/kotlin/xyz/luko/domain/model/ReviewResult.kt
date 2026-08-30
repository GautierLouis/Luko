package xyz.luko.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResult(
    val isStreakUpdated: Boolean,
    val newStreak: Int,
    val hasLevelUp: Boolean,
    val levels: Map<Int, List<Int>>,
    val sessionResponse: List<ReviewResponse> = emptyList()
)

@Serializable
data class ReviewResponse(
    val code: Int,
    val pinyin: String,
    val strokes: List<Stroke>,
    val references: List<Stroke>,
    val recognitionResult: String,
    val difficultyLevel: DifficultyLevel,
    val comparisonResult: StrokeComparisonResult
)
