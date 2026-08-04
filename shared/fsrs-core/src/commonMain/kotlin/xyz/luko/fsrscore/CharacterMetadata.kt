package xyz.luko.fsrscore

import kotlinx.serialization.Serializable

@Serializable
data class StrokeComparisonResult(
    val overallAccuracy: Float,
    val strokeAccuracies: List<Float>,
    val orderAccuracy: Float,
    val strokeCountCorrect: Boolean,
    val strokeOrderCorrect: Boolean,
    val details: ComparisonDetails,
)

@Serializable
data class ComparisonDetails(
    val pathSimilarity: Float,
    val startPointAccuracy: Float,
    val endPointAccuracy: Float,
    val directionAccuracy: Float,
)

enum class Grade(val value: Double, val severity: Int) {
    FORGOT(1.0, 0), HARD(2.0, 1), GOOD(3.0, 2), EASY(4.0, 3)
}


data class Offset(val x: Float, val y: Float) {
    operator fun plus(other: Offset): Offset {
        return Offset(x + other.x, y + other.y)
    }

    operator fun minus(other: Offset): Offset {
        return Offset(x - other.x, y - other.y)
    }

    operator fun times(scalar: Float): Offset {
        return Offset(x * scalar, y * scalar)
    }

    operator fun div(scalar: Float): Offset {
        return Offset(x / scalar, y / scalar)
    }
}

