package xyz.luko.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String, // Instant
    val duration: Long, // Duration
    val difficulty: String,
    val questionsCount: Int,
    val score: Int,
    val accuracy: Double,
)
