package xyz.luko.domain.model

import kotlin.time.Duration

data class Statistics(
    val averageTime: Duration,
    val averageQuestionsCount: Float,
    val averageAccuracy: Double,
    val averageDifficulty: DifficultyLevel?,
    val sessionCount: Int,
)
