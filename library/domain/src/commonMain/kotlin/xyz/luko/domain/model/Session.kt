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
    val accuracy: Double,
)

data class TemporarySession(
    val id: Long = 0,
    val date: Instant,
    val duration: Duration,
    val difficulty: DifficultyLevel,
    val questionsCount: Int,
)

data class TemporaryResponse(
    val id: Long = 0,
    val code: Int,
    val pinyin: String,
    val strokes: List<Stroke>,
    val references: List<Stroke>,
    val recognitionResult: String,
    val difficultyLevel: DifficultyLevel,
)
