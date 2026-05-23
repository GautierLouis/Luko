package xyz.luko.server.domain.usecase

import kotlin.math.sqrt

object PointReducer {

    fun execute(points: List<Pair<Float, Float>>, epsilon: Float = 15f): List<Pair<Float, Float>> {
        if (points.size < 3) return points

        val (index, maxDist) = findFarthest(points)

        return if (maxDist > epsilon) {
            val left = execute(points.subList(0, index + 1), epsilon)
            val right = execute(points.subList(index, points.size), epsilon)
            left.dropLast(1) + right
        } else {
            listOf(points.first(), points.last())
        }
    }

    private fun findFarthest(points: List<Pair<Float, Float>>): Pair<Int, Float> {
        val start = points.first()
        val end = points.last()
        var maxDist = 0f
        var maxIndex = 0

        for (i in 1 until points.size - 1) {
            val dist = perpendicularDistance(points[i], start, end)
            if (dist > maxDist) {
                maxDist = dist
                maxIndex = i
            }
        }

        return maxIndex to maxDist
    }

    private fun perpendicularDistance(
        point: Pair<Float, Float>,
        lineStart: Pair<Float, Float>,
        lineEnd: Pair<Float, Float>,
    ): Float {
        val dx = lineEnd.first - lineStart.first
        val dy = lineEnd.second - lineStart.second

        // Degenerate case: start == end, just return point-to-point distance
        if (dx == 0f && dy == 0f) {
            return hypot(point.first - lineStart.first, point.second - lineStart.second)
        }

        val t =
            ((point.first - lineStart.first) * dx + (point.second - lineStart.second) * dy) / (dx * dx + dy * dy)
        val nearestX = lineStart.first + t * dx
        val nearestY = lineStart.second + t * dy

        return hypot(point.first - nearestX, point.second - nearestY)
    }

    private fun hypot(dx: Float, dy: Float) = sqrt(dx * dx + dy * dy)
}
