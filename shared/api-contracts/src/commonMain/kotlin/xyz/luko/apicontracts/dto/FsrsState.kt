package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class FsrsState(
    val difficulty: Double,
    val stability: Double,
    val level: Int,
    val lastReviewedAt: Long,
)
