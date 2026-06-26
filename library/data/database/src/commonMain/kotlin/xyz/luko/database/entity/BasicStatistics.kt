package xyz.luko.database.entity

import androidx.room.DatabaseView

@DatabaseView
data class BasicStatistics(
    val averageTime: Long,
    val averageQuestionsCount: Float,
    val averageAccuracy: Float,
    val difficulties: List<String>?,
    val sessionCount: Int,
    val uniqueDates: List<String>?,
)
