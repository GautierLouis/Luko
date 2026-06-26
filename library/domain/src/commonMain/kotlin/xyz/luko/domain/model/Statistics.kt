package xyz.luko.domain.model

import kotlin.time.Duration

data class Statistics(
    val averageTime: Duration,
    val averageQuestionsCount: Float,
    val averageAccuracy: Float,
    val averageDifficulty: DifficultyLevel?,
    val currentDayStreak: Int,
    val sessionCount: Int,
)
