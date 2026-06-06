package xyz.luko.server.domain.usecase

import xyz.luko.apicontracts.dto.PointDto
import xyz.luko.apicontracts.dto.StrokeDto

object BezierCurveConverter {

    fun execute(
        points: List<Pair<Float, Float>>,
    ): StrokeDto {
        val points: List<PointDto> = calculate(points)
        return StrokeDto(points)
    }

    /**
     * Converts a polyline of points into a smooth cubic Bézier as a structured [PointDto] object.
     * Each segment between consecutive points becomes a cubic Bézier curve
     * whose control points are derived from the neighbouring points.
     *
     * @param tension 0f = loose, 0.5f = default Catmull-Rom, 1f = tight
     */
    private fun calculate(
        points: List<Pair<Float, Float>>,
        tension: Float = 0.3f
    ): List<PointDto> {
        if (points.isEmpty()) return emptyList()

        // First anchor: move-to, no incoming curve
        val result =
            mutableListOf<PointDto>(PointDto.Straight(points[0].first.round(), points[0].second.round()))

        if (points.size == 1) return result

        // Straight line case — no control points
        if (points.size == 2) {
            result += PointDto.Straight(points[1].first.round(), points[1].second.round())
            return result
        }

        for (i in 0 until points.size - 1) {
            val p0 = points.getOrElse(i - 1) { points[i] }
            val p1 = points[i]
            val p2 = points[i + 1]
            val p3 = points.getOrElse(i + 2) { points[i + 1] }

            val cp1x = p1.first + (p2.first - p0.first) * tension / 3f
            val cp1y = p1.second + (p2.second - p0.second) * tension / 3f
            val cp2x = p2.first - (p3.first - p1.first) * tension / 3f
            val cp2y = p2.second - (p3.second - p1.second) * tension / 3f

            result += PointDto.Curved(
                x = p2.first.round(),
                y = p2.second.round(),
                cp1x = cp1x.round(),
                cp1y = cp1y.round(),
                cp2x = cp2x.round(),
                cp2y = cp2y.round()
            )
        }

        return result
    }

    private fun Float.round() = "%.2f".format(this).toFloat()

}
