package xyz.luko.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("sessionId"), Index("code")],
)
data class ResponseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sessionId: Int,
    val code: Int,
    val overallAccuracy: Float,
    val response: String,
)
