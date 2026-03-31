package com.louisgautier.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String, // Instant
    val duration: Long, // Duration
    val difficulty: String,
    val questionsCount: Int,
    val score: Int,
)

@Serializable
data class EmbeddedResponse(
    val code: Int,
    val statistics: EmbeddedStrokeComparisonResult,
    val strokes: List<EmbeddedStroke>
)

@Serializable
data class EmbeddedStroke(
    val points: List<EmbeddedCoordinates>
)

@Serializable
data class EmbeddedCoordinates(
    val x: Float,
    val y: Float,
)

@Serializable
data class EmbeddedStrokeComparisonResult(
    val overallAccuracy: Float, // 0-100
    val strokeAccuracies: List<Float>,
    val orderAccuracy: Float,
    val details: EmbeddedComparisonDetails
)

@Serializable
data class EmbeddedComparisonDetails(
    val pathSimilarity: Float,
    val startPointAccuracy: Float,
    val endPointAccuracy: Float,
    val directionAccuracy: Float,
    val orderPenalty: Float
)