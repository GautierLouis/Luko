package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResultDto(
    val isStreakUpdated: Boolean,
    val newStreak: Int,
    val hasLevelUp: Boolean,
    val levels: Map<Int, List<Int>>,
    val strokeComparison: Map<Int, StrokeComparisonResultDto>
)
