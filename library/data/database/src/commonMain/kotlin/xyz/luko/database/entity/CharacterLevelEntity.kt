package xyz.luko.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["code"], unique = true)])
data class CharacterLevelEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val code: Int,
    val level: Int,
    val hasBeenPromoted: Boolean,
)
