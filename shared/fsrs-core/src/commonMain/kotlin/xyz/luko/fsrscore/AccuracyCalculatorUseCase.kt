package xyz.luko.fsrscore

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

class AccuracyUseCase {
    private companion object {
        private const val TOLERANCE_RADIUS: Float = 80f
        private const val SAMPLING_POINTS: Int = 20
        private const val MISSING_STROKE_PENALTY_MULTIPLIER: Float = 0.8f
        private const val ORDER_ACCURACY_THRESHOLD: Float = 100f // to calibrate — see note below
    }

    // NOTE01: assumes reference and userStroke are already in the same normalized coordinate
    // space (0-1024, natural orientation) before calling this — see toNormalizedSpace / toGlyphSpace.
    // This class does no spatial normalization itself.
    // NOTE02: replace List<[Offset]> (custom object to mimic androidx.graphic.offset)
    fun calculate(
        reference: List<List<Offset>>,
        userStroke: List<List<Offset>>,
    ): StrokeComparisonResult {
        if (reference.isEmpty()) {
            return StrokeComparisonResult(
                overallAccuracy = 0f,
                strokeAccuracies = emptyList(),
                orderAccuracy = 0f,
                strokeCountCorrect = false,
                strokeOrderCorrect = false,
                details = ComparisonDetails(0f, 0f, 0f, 0f),
            )
        }

        val strokeCount = reference.size
        val userStrokeCount = userStroke.size
        val maxCount = maxOf(strokeCount, userStrokeCount)
        val hasCountMismatch = userStrokeCount != strokeCount

        val strokeAccuracies = mutableListOf<Float>()
        var totalPathSimilarity = 0f
        var totalStartAccuracy = 0f
        var totalEndAccuracy = 0f
        var totalDirectionAccuracy = 0f

        // Iterate to maxCount, not just min/reference count, so both missing AND extra
        // strokes are symmetrically scored as zero rather than one side being silently dropped.
        for (i in 0 until maxCount) {
            val refStroke = reference.getOrNull(i)
            val userDrawnStroke = userStroke.getOrNull(i)

            val strokeResult = if (refStroke != null && userDrawnStroke != null) {
                compareStroke(refStroke, userDrawnStroke)
            } else {
                SingleStrokeResult(0f, 0f, 0f, 0f, 0f)
            }

            strokeAccuracies.add(strokeResult.accuracy)
            totalPathSimilarity += strokeResult.pathSimilarity
            totalStartAccuracy += strokeResult.startPointAccuracy
            totalEndAccuracy += strokeResult.endPointAccuracy
            totalDirectionAccuracy += strokeResult.directionAccuracy
        }

        val details = ComparisonDetails(
            pathSimilarity = totalPathSimilarity / maxCount,
            startPointAccuracy = totalStartAccuracy / maxCount,
            endPointAccuracy = totalEndAccuracy / maxCount,
            directionAccuracy = totalDirectionAccuracy / maxCount,

        )

        val countPenalty = if (hasCountMismatch) MISSING_STROKE_PENALTY_MULTIPLIER else 1f
        val avgAccuracy = strokeAccuracies.average().toFloat()
        val overallAccuracy = (avgAccuracy * countPenalty).coerceIn(0f, 100f)

        val orderAccuracy = calculateOrderAccuracy(reference, userStroke)

        return StrokeComparisonResult(
            overallAccuracy = overallAccuracy,
            strokeAccuracies = strokeAccuracies,
            orderAccuracy = orderAccuracy,
            strokeCountCorrect = userStrokeCount == strokeCount,
            strokeOrderCorrect = orderAccuracy >= ORDER_ACCURACY_THRESHOLD,
            details = details,
        )
    }

    private data class SingleStrokeResult(
        val accuracy: Float,
        val pathSimilarity: Float,
        val startPointAccuracy: Float,
        val endPointAccuracy: Float,
        val directionAccuracy: Float,
    )

    private fun compareStroke(refStroke: List<Offset>, drawnStroke: List<Offset>): SingleStrokeResult {
        if (refStroke.isEmpty() || drawnStroke.isEmpty()) {
            return SingleStrokeResult(0f, 0f, 0f, 0f, 0f)
        }

        val startAccuracy = comparePoints(refStroke.first(), drawnStroke.first())
        val endAccuracy = comparePoints(refStroke.last(), drawnStroke.last())
        val directionAccuracy = compareDirection(refStroke, drawnStroke)
        val pathSimilarity = comparePathShape(refStroke, drawnStroke)

        // Weights: start 10%, end 10%, direction 30%, path shape 50%
        val accuracy = (startAccuracy * 0.10f + endAccuracy * 0.10f +
            directionAccuracy * 0.30f + pathSimilarity * 0.50f) * 100f

        return SingleStrokeResult(
            accuracy = accuracy,
            pathSimilarity = pathSimilarity * 100f,
            startPointAccuracy = startAccuracy * 100f,
            endPointAccuracy = endAccuracy * 100f,
            directionAccuracy = directionAccuracy * 100f,
        )
    }

    private fun comparePoints(ref: Offset, user: Offset): Float {
        val distance = sqrt((ref.x - user.x).pow(2) + (ref.y - user.y).pow(2))
        return (1f - (distance / TOLERANCE_RADIUS).coerceIn(0f, 1f)).pow(0.5f)
    }

    private fun compareDirection(ref: List<Offset>, user: List<Offset>): Float {
        if (ref.size < 2 || user.size < 2) return 0f

        val refVector = ref.last() - ref.first()
        val userVector = user.last() - user.first()

        val refAngle = atan2(refVector.y.toDouble(), refVector.x.toDouble())
        val userAngle = atan2(userVector.y.toDouble(), userVector.x.toDouble())

        var angleDiff = abs(refAngle - userAngle)
        if (angleDiff > PI) angleDiff = 2 * PI - angleDiff

        return (1f - (angleDiff / PI).toFloat()).coerceIn(0f, 1f)
    }

    /** Average point-to-point distance after resampling both strokes to the same fixed point count. */
    private fun comparePathShape(ref: List<Offset>, user: List<Offset>): Float {
        val refSampled = resampleStroke(ref, SAMPLING_POINTS)
        val userSampled = resampleStroke(user, SAMPLING_POINTS)

        var totalDistance = 0f
        for (i in 0 until SAMPLING_POINTS) {
            totalDistance += distance(refSampled[i], userSampled[i])
        }

        val avgDistance = totalDistance / SAMPLING_POINTS
        return (1f - (avgDistance / (TOLERANCE_RADIUS * 3)).coerceIn(0f, 1f))
    }

    /** Always resamples to exactly [pointCount], regardless of the input stroke's raw point density. */
    private fun resampleStroke(stroke: List<Offset>, pointCount: Int): List<Offset> {
        if (stroke.isEmpty()) return emptyList()
        if (stroke.size == 1 || pointCount == 1) return List(pointCount) { stroke.first() }

        var totalLength = 0f
        for (i in 0 until stroke.size - 1) {
            totalLength += distance(stroke[i], stroke[i + 1])
        }

        if (totalLength < 0.001f) {
            return List(pointCount) { stroke.first() }
        }

        val segmentLength = totalLength / (pointCount - 1)
        val resampled = mutableListOf<Offset>()
        resampled.add(stroke.first())

        var accumulatedLength = 0f
        var currentSegment = 0

        for (i in 1 until pointCount - 1) {
            val targetLength = i * segmentLength

            while (currentSegment < stroke.size - 1) {
                val segLength = distance(stroke[currentSegment], stroke[currentSegment + 1])

                if (accumulatedLength + segLength >= targetLength) {
                    val remainingLength = targetLength - accumulatedLength
                    val ratio = if (segLength > 0) remainingLength / segLength else 0f
                    resampled.add(lerp(stroke[currentSegment], stroke[currentSegment + 1], ratio))
                    break
                }

                accumulatedLength += segLength
                currentSegment++
            }

            if (currentSegment >= stroke.size - 1 && resampled.size <= i) {
                resampled.add(resampled.last())
            }
        }

        resampled.add(stroke.last())
        return resampled
    }

    private fun distance(p1: Offset, p2: Offset): Float =
        sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2))

    private fun lerp(start: Offset, end: Offset, ratio: Float): Offset =
        Offset(start.x + (end.x - start.x) * ratio, start.y + (end.y - start.y) * ratio)

    /** Positional check: does userStrokes[i]'s start point land near referenceStrokes[i]'s start point. */
    private fun calculateOrderAccuracy(
        referenceStrokes: List<List<Offset>>,
        userStrokes: List<List<Offset>>,
    ): Float {
        if (referenceStrokes.isEmpty() || userStrokes.isEmpty()) return 0f

        var correctOrder = 0
        val minSize = minOf(referenceStrokes.size, userStrokes.size)

        for (i in 0 until minSize) {
            val refStart = referenceStrokes[i].firstOrNull() ?: continue
            val userStart = userStrokes[i].firstOrNull() ?: continue

            if (distance(refStart, userStart) < TOLERANCE_RADIUS * 1.5f) {
                correctOrder++
            }
        }

        return (correctOrder.toFloat() / referenceStrokes.size) * 100f
    }
}
