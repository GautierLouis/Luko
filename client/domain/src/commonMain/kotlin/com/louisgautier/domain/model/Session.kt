package com.louisgautier.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class Session(
    val id: Int = 0,
    val date: Instant,
    val duration: Duration,
    val difficulty: Difficulty,
    val responses: List<Response>,
    val score: Int,
)

@Serializable
data class Response(
    val code: Int,
    val statistics: StrokeComparisonResult,
    val strokes: List<Stroke>
)

@Serializable
data class StrokeComparisonResult(
    val overallAccuracy: Float, // 0-100
    val strokeAccuracies: List<Float>,
    val orderAccuracy: Float,
    val details: ComparisonDetails
)

@Serializable
data class ComparisonDetails(
    val pathSimilarity: Float,
    val startPointAccuracy: Float,
    val endPointAccuracy: Float,
    val directionAccuracy: Float,
    val orderPenalty: Float
)