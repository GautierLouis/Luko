package xyz.luko.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.Instant

@Serializable
data class Session(
    val id: Long = 0,
    val date: Instant,
    val duration: Duration,
    val difficulty: DifficultyLevel,
    val questionsCount: Int,
    val score: Int,
)
