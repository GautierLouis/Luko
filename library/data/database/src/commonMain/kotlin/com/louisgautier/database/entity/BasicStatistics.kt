package com.louisgautier.database.entity

import androidx.room.DatabaseView

@DatabaseView
data class BasicStatistics(
    val totalScore: Int,
    val averageTime: Long,
    val sessionCount: Int,
    val difficulties: List<String>?,
    val uniqueDates: List<String>?
)