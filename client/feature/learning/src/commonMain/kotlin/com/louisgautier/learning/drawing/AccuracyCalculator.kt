package com.louisgautier.learning.drawing

import androidx.compose.ui.geometry.Offset
import com.louisgautier.domain.model.ComparisonDetails
import com.louisgautier.domain.model.StrokeComparisonResult
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

class AccuracyCalculator(
    private val toleranceRadius: Float = 50f, // Acceptable deviation in pixels
    private val samplingPoints: Int = 20 // Number of points to sample for comparison
) {

    fun calculate(
        reference: List<List<Offset>>,
        userStroke: List<List<Offset>>
    ): StrokeComparisonResult {

// Check if stroke counts match
        if (reference.isEmpty()) {
            return StrokeComparisonResult(
                0f, emptyList(), 0f,
                ComparisonDetails(0f, 0f, 0f, 0f, 0f)
            )
        }

        val strokeCount = reference.size
        val userStrokeCount = userStroke.size

        // Penalize for wrong number of strokes
        val countPenalty = if (userStrokeCount != strokeCount) {
            0.5f // 50% penalty for wrong stroke count
        } else {
            1f
        }

        // Compare each stroke
        val strokeAccuracies = mutableListOf<Float>()
        var totalPathSimilarity = 0f
        var totalStartAccuracy = 0f
        var totalEndAccuracy = 0f
        var totalDirectionAccuracy = 0f
        var totalOrderPenalty = 0f

        val minCount = minOf(strokeCount, userStrokeCount)

        for (i in 0 until minCount) {
            val refStroke = reference[i]
            val userStroke = userStroke[i]

            val strokeResult = compareStroke(refStroke, userStroke)
            strokeAccuracies.add(strokeResult.accuracy)

            totalPathSimilarity += strokeResult.pathSimilarity
            totalStartAccuracy += strokeResult.startPointAccuracy
            totalEndAccuracy += strokeResult.endPointAccuracy
            totalDirectionAccuracy += strokeResult.directionAccuracy
            totalOrderPenalty += strokeResult.orderPenalty
        }

        // Add zero scores for missing strokes
        for (i in minCount until strokeCount) {
            strokeAccuracies.add(0f)
        }

        val avgCount = strokeCount.toFloat()
        val details = ComparisonDetails(
            pathSimilarity = totalPathSimilarity / avgCount,
            startPointAccuracy = totalStartAccuracy / avgCount,
            endPointAccuracy = totalEndAccuracy / avgCount,
            directionAccuracy = totalDirectionAccuracy / avgCount,
            orderPenalty = totalOrderPenalty / avgCount
        )

        // Calculate overall accuracy
        val avgAccuracy = strokeAccuracies.average().toFloat()
        val overallAccuracy = (avgAccuracy * countPenalty).coerceIn(0f, 100f)

        val orderAccuracy = calculateOrderAccuracy(reference, userStroke)

        return StrokeComparisonResult(
            overallAccuracy = overallAccuracy,
            strokeAccuracies = strokeAccuracies,
            orderAccuracy = orderAccuracy,
            details = details
        )
    }

    private data class SingleStrokeResult(
        val accuracy: Float,
        val pathSimilarity: Float,
        val startPointAccuracy: Float,
        val endPointAccuracy: Float,
        val directionAccuracy: Float,
        val orderPenalty: Float
    )

    private fun compareStroke(
        refStroke: List<Offset>,
        userStroke: List<Offset>,
    ): SingleStrokeResult {

        if (refStroke.isEmpty() || userStroke.isEmpty()) {
            return SingleStrokeResult(0f, 0f, 0f, 0f, 0f, 1f)
        }

        // 1. Start point accuracy (15% weight)
        val startAccuracy = comparePoints(refStroke.first(), userStroke.first())

        // 2. End point accuracy (15% weight)
        val endAccuracy = comparePoints(refStroke.last(), userStroke.last())

        // 3. Direction accuracy (20% weight)
        val directionAccuracy = compareDirection(refStroke, userStroke)

        // 4. Path similarity (50% weight) - most important
        val pathSimilarity = comparePathShape(refStroke, userStroke)

        // Calculate weighted accuracy
        val accuracy = (
                startAccuracy * 0.15f +
                        endAccuracy * 0.15f +
                        directionAccuracy * 0.20f +
                        pathSimilarity * 0.50f
                ) * 100f

        return SingleStrokeResult(
            accuracy = accuracy,
            pathSimilarity = pathSimilarity * 100f,
            startPointAccuracy = startAccuracy * 100f,
            endPointAccuracy = endAccuracy * 100f,
            directionAccuracy = directionAccuracy * 100f,
            orderPenalty = 0f
        )
    }

    /**
     * Compare two points with tolerance
     */
    private fun comparePoints(ref: Offset, user: Offset): Float {
        val distance = sqrt(
            (ref.x - user.x) * (ref.x - user.x) +
                    (ref.y - user.y) * (ref.y - user.y)
        )

        // Linear decay from 1.0 to 0.0 based on tolerance
        return (1f - (distance / toleranceRadius).coerceIn(0f, 1f))
    }

    /**
     * Compare stroke direction (start to end vector)
     */
    private fun compareDirection(ref: List<Offset>, user: List<Offset>): Float {
        if (ref.size < 2 || user.size < 2) return 0f

        // Calculate direction vectors
        val refVector = ref.last() - ref.first()
        val userVector = user.last() - user.first()

        // Calculate angles
        val refAngle = atan2(refVector.y.toDouble(), refVector.x.toDouble())
        val userAngle = atan2(userVector.y.toDouble(), userVector.x.toDouble())

        // Calculate angle difference (0 to PI)
        var angleDiff = abs(refAngle - userAngle)
        if (angleDiff > PI) angleDiff = 2 * PI - angleDiff

        // Convert to similarity score (0 to 1)
        return (1f - (angleDiff / PI).toFloat()).coerceIn(0f, 1f)
    }

    /**
     * Compare the shape of two stroke paths using Hausdorff distance
     */
    private fun comparePathShape(ref: List<Offset>, user: List<Offset>): Float {
        // Normalize both strokes to same number of points
        val refSampled = resampleStroke(ref, samplingPoints)
        val userSampled = resampleStroke(user, samplingPoints)

        // Calculate average point-to-point distance
        var totalDistance = 0f
        for (i in refSampled.indices) {
            val distance = sqrt(
                (refSampled[i].x - userSampled[i].x) * (refSampled[i].x - userSampled[i].x) +
                        (refSampled[i].y - userSampled[i].y) * (refSampled[i].y - userSampled[i].y)
            )
            totalDistance += distance
        }

        val avgDistance = totalDistance / samplingPoints

        // Convert to similarity score
        return (1f - (avgDistance / (toleranceRadius * 2)).coerceIn(0f, 1f))
    }

    /**
     * Resample stroke to fixed number of points
     */
    private fun resampleStroke(stroke: List<Offset>, pointCount: Int): List<Offset> {
        if (stroke.isEmpty()) return emptyList()
        if (stroke.size == 1) return List(pointCount) { stroke.first() }

        // If stroke has fewer points than requested, use the smaller count
        val actualPointCount = minOf(pointCount, stroke.size)
        if (actualPointCount <= 1) return listOf(stroke.first())

        // Calculate total path length
        var totalLength = 0f
        for (i in 0 until stroke.size - 1) {
            totalLength += distance(stroke[i], stroke[i + 1])
        }

        // If path has zero length, return duplicates of first point
        if (totalLength == 0f || totalLength < 0.001f) {
            return List(actualPointCount) { stroke.first() }
        }

        val segmentLength = totalLength / (actualPointCount - 1)
        val resampled = mutableListOf<Offset>()
        resampled.add(stroke.first())

        var accumulatedLength = 0f
        var currentSegment = 0

        for (i in 1 until actualPointCount - 1) {
            val targetLength = i * segmentLength

            // Move forward through stroke until we reach target length
            while (currentSegment < stroke.size - 1) {
                val segLength = distance(stroke[currentSegment], stroke[currentSegment + 1])

                if (accumulatedLength + segLength >= targetLength) {
                    // Interpolate point on current segment
                    val remainingLength = targetLength - accumulatedLength
                    val ratio = if (segLength > 0) remainingLength / segLength else 0f
                    val point = lerp(stroke[currentSegment], stroke[currentSegment + 1], ratio)
                    resampled.add(point)
                    break
                }

                accumulatedLength += segLength
                currentSegment++
            }

            // Safety check: if we've run out of segments, duplicate last added point
            if (currentSegment >= stroke.size - 1 && resampled.size <= i) {
                resampled.add(resampled.last())
            }
        }

        resampled.add(stroke.last())
        return resampled
    }

    private fun distance(p1: Offset, p2: Offset): Float {
        return sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y))
    }

    private fun lerp(start: Offset, end: Offset, ratio: Float): Offset {
        return Offset(
            start.x + (end.x - start.x) * ratio,
            start.y + (end.y - start.y) * ratio
        )
    }

    /**
     * Calculate how well the user followed the stroke order
     */
    private fun calculateOrderAccuracy(
        referenceStrokes: List<List<Offset>>,
        userStrokes: List<List<Offset>>
    ): Float {
        if (referenceStrokes.isEmpty() || userStrokes.isEmpty()) return 0f

        var correctOrder = 0
        val minSize = minOf(referenceStrokes.size, userStrokes.size)

        for (i in 0 until minSize) {
            // Check if the user's i-th stroke matches the reference i-th stroke position
            val refStart = referenceStrokes[i].firstOrNull() ?: continue
            val userStart = userStrokes[i].firstOrNull() ?: continue

            val dist = distance(refStart, userStart)
            if (dist < toleranceRadius * 1.5f) {
                correctOrder++
            }
        }

        return (correctOrder.toFloat() / referenceStrokes.size) * 100f
    }
}

fun analyzeUserDrawing(
    referenceStrokes: List<List<Offset>>,
    userStrokes: List<List<Offset>>
): StrokeComparisonResult {
    val analyzer = AccuracyCalculator(
        toleranceRadius = 50f,
        samplingPoints = 20
    )

    return analyzer.calculate(referenceStrokes, userStrokes)
}