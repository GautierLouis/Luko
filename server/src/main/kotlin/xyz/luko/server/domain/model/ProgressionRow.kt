package xyz.luko.server.domain.model

import xyz.luko.apicontracts.dto.StrokeComparisonResultDto

data class ProgressionRow(
    val code: Int,
    val stability: Double,
    val difficulty: Double,
    val level: Int,
    val levelUp: Boolean,
    val nextReviewDueAt: Long,
    val strokeComparison: StrokeComparisonResultDto
)
