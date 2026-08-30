package xyz.luko.fsrscore.internal

import xyz.luko.apicontracts.dto.StrokeDto
import xyz.luko.fsrscore.model.ComparablePoint
import xyz.luko.fsrscore.model.ComparableStroke
import xyz.luko.fsrscore.model.ComparisonDetails
import xyz.luko.fsrscore.model.StrokeComparisonResult
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

internal object StrokeComparisonUseCase {

    // Acceptable deviation in the normalized 0-1024 coordinate space.
    // Placeholder — needs calibration against real drawing data (see log-and-inspect note below).
    private const val TOLERANCE_RADIUS: Float = 80f
    private const val MISSING_STROKE_PENALTY_MULTIPLIER: Float = 0.8f // to calibrate
    private const val ORDER_ACCURACY_THRESHOLD: Float = 100f

    fun strokeToComparableStroke(strokes: List<StrokeDto>): List<ComparableStroke> =
        strokes.map { s -> ComparableStroke(s.points.map { p -> ComparablePoint(p.x, p.y) }) }

    /**
     * Assumes [reference] and [userStroke] are already in the same normalized coordinate
     * space (0-1024, natural orientation) before calling this. This class performs no
     * spatial normalization itself — see toNormalizedSpace / toGlyphSpace upstream.
     */
    fun calculate(
        reference: List<ComparableStroke>,
        userStroke: List<ComparableStroke>,
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

        // Iterate to maxCount so both missing AND extra strokes are symmetrically
        // scored as zero, rather than one side being silently dropped.
        for (i in 0 until maxCount) {
            val refStroke = reference.getOrNull(i)
            val drawnStroke = userStroke.getOrNull(i)

            @Suppress("UNCHECKED_CAST")
            val strokeResult = if (refStroke != null && drawnStroke != null) {
                compareStroke(refStroke.points, drawnStroke.points)
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
            strokeCountCorrect = !hasCountMismatch,
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

    private fun compareStroke(
        refStroke: List<ComparablePoint>,
        drawnStroke: List<ComparablePoint>
    ): SingleStrokeResult {
        if (refStroke.isEmpty() || drawnStroke.isEmpty()) {
            return SingleStrokeResult(0f, 0f, 0f, 0f, 0f)
        }

        val startAccuracy = comparePoints(refStroke.first(), drawnStroke.first())
        val endAccuracy = comparePoints(refStroke.last(), drawnStroke.last())
        val directionAccuracy = compareDirection(refStroke, drawnStroke)
        val pathSimilarity = comparePathShape(refStroke, drawnStroke)

        // Weights: start 10%, end 10%, direction 30%, path shape (DTW) 50%
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

    /** Linear falloff — 1.0 at zero distance, 0.0 at or beyond TOLERANCE_RADIUS. */
    private fun comparePoints(ref: ComparablePoint, user: ComparablePoint): Float {
        val d = distance(ref, user)
        return 1f - (d / TOLERANCE_RADIUS).coerceIn(0f, 1f)
    }

    private fun compareDirection(ref: List<ComparablePoint>, user: List<ComparablePoint>): Float {
        if (ref.size < 2 || user.size < 2) return 0f

        val refVector = ref.last() - ref.first()
        val userVector = user.last() - user.first()

        val refAngle = atan2(refVector.y.toDouble(), refVector.x.toDouble())
        val userAngle = atan2(userVector.y.toDouble(), userVector.x.toDouble())

        var angleDiff = abs(refAngle - userAngle)
        if (angleDiff > PI) angleDiff = 2 * PI - angleDiff

        // NOTE: only compares the overall start→end vector, not per-segment direction.
        // Curved strokes with similar endpoints can score high here even if the path
        // between them diverges — path shape (DTW) is what catches that case.
        return (1f - (angleDiff / PI).toFloat()).coerceIn(0f, 1f)
    }

    /**
     * Path shape similarity via Dynamic Time Warping — a standard, well-documented technique
     * for comparing two point sequences of different lengths/densities without requiring
     * them to be resampled to matching point counts first. Handles the reference-is-sparse-
     * and-curved vs. user-drawing-is-dense mismatch natively.
     */
    private fun comparePathShape(ref: List<ComparablePoint>, user: List<ComparablePoint>): Float {
        val avgDtwDistance = dtwDistance(ref, user)
        return (1f - (avgDtwDistance / TOLERANCE_RADIUS).coerceIn(0f, 1f))
    }

    /**
     * Dynamic Time Warping distance between two point sequences.
     * Returns the optimal-alignment cumulative distance, normalized by path length
     * so longer strokes aren't unfairly penalized relative to short ones.
     */
    private fun dtwDistance(a: List<ComparablePoint>, b: List<ComparablePoint>): Float {
        val n = a.size
        val m = b.size
        if (n == 0 || m == 0) return Float.MAX_VALUE

        val dp = Array(n + 1) { FloatArray(m + 1) { Float.MAX_VALUE } }
        dp[0][0] = 0f

        for (i in 1..n) {
            for (j in 1..m) {
                val cost = distance(a[i - 1], b[j - 1])
                dp[i][j] = cost + minOf(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])
            }
        }

        return dp[n][m] / maxOf(n, m)
    }

    private fun distance(p1: ComparablePoint, p2: ComparablePoint): Float =
        sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2))

    /**
     * Positional check: does userStrokes\[i]'s start point land near referenceStrokes\[i]'s
     * start point. Does not detect swapped strokes whose start points happen to be close —
     * a known limitation, acceptable for now given how rare that specific case is.
     */
    private fun calculateOrderAccuracy(
        referenceStrokes: List<ComparableStroke>,
        userStrokes: List<ComparableStroke>,
    ): Float {
        if (referenceStrokes.isEmpty() || userStrokes.isEmpty()) return 0f

        var correctOrder = 0
        val minSize = minOf(referenceStrokes.size, userStrokes.size)

        for (i in 0 until minSize) {
            val refStart = referenceStrokes[i].points.firstOrNull() ?: continue
            val userStart = userStrokes[i].points.firstOrNull() ?: continue

            if (distance(refStart, userStart) < TOLERANCE_RADIUS * 1.5f) {
                correctOrder++
            }
        }

        return (correctOrder.toFloat() / referenceStrokes.size) * 100f
    }
}
