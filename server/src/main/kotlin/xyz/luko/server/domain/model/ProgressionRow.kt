package xyz.luko.server.domain.model

data class ProgressionRow(
    val code: Int,
    val stability: Double,
    val difficulty: Double,
    val level: Int,
    val levelUp: Boolean,
    val nextReviewDueAt: Long,
)
