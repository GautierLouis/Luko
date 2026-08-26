package xyz.luko.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResult(
    val isStreakUpdated: Boolean,
    val newStreak: Int,
    val hasLevelUp: Boolean,
    val levels: Map<Int, List<Int>>,
    val strokeComparison: List<StrokeComparisonResult>
)
