import androidx.compose.ui.geometry.Offset
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

fun dtwSimilaritySymmetric(s: List<Offset>, t: List<Offset>): Double {
    if (s.isEmpty() || t.isEmpty()) return 0.0
    val dtw = dtwDistance(s, t)
    if (dtw.isInfinite() || dtw.isNaN()) return 0.0
    val steps = max(s.size, t.size)
    val avgError = dtw / steps
    val denom = max(bboxDiagonal(s), bboxDiagonal(t))
    val relativeError = if (denom > 1e-9) avgError / denom else if (avgError <= 1e-9) 0.0 else 1.0
    return (1.0 - relativeError).coerceIn(0.0, 1.0) * 100.0
}

private fun dtwDistance(
    s: List<Offset>,
    t: List<Offset>
): Double {
    val n = s.size
    val m = t.size

    if (n == 0 || m == 0) {
        return Double.POSITIVE_INFINITY
    }

    // Create dp table filled with +Inf
    val dtw = Array(n + 1) { DoubleArray(m + 1) { Double.POSITIVE_INFINITY } }
    dtw[0][0] = 0.0

    fun dist(a: Offset, b: Offset): Double =
        hypot((a.x - b.x).toDouble(), (a.y - b.y).toDouble())

    for (i in 1..n) {
        for (j in 1..m) {
            val cost = dist(s[i - 1], t[j - 1])
            // pick min of three neighbors
            val bestPrev = min(
                dtw[i - 1][j],    // insertion
                min(dtw[i][j - 1], dtw[i - 1][j - 1]) // deletion, match
            )
            dtw[i][j] = cost + bestPrev
        }
    }

    val result = dtw[n][m]

    return result
}

private fun bboxDiagonal(path: List<Offset>): Double {
    if (path.isEmpty()) return 0.0
    val minX = path.minOf { it.x }
    val maxX = path.maxOf { it.x }
    val minY = path.minOf { it.y }
    val maxY = path.maxOf { it.y }
    return hypot((maxX - minX).toDouble(), (maxY - minY).toDouble())
}
