package xyz.luko.server.domain.usecase.parser

object CatmullRomToBezier {

    /**
     * Converts a polyline of points into a smooth cubic Bézier path string.
     * Each segment between consecutive points becomes a cubic Bézier curve
     * whose control points are derived from the neighbouring points.
     *
     * @param tension 0f = loose, 0.5f = default Catmull-Rom, 1f = tight
     */
    fun toPath(points: List<Pair<Float, Float>>, tension: Float = 0.3f): String {
        if (points.size < 2) return points.firstOrNull()
            ?.let { (x, y) -> "M ${x.f()} ${y.f()}" } ?: ""

        // Only 2 points — just a straight line, no curve needed
        if (points.size == 2) {
            val (x0, y0) = points[0]
            val (x1, y1) = points[1]
            return "M ${x0.f()} ${y0.f()} L ${x1.f()} ${y1.f()}"
        }

        val sb = StringBuilder()
        val (x0, y0) = points.first()
        sb.append("M ${x0.f()} ${y0.f()} ")

        for (i in 0 until points.size - 1) {
            val p0 = points.getOrElse(i - 1) { points[i] }
            val p1 = points[i]
            val p2 = points[i + 1]
            val p3 = points.getOrElse(i + 2) { points[i + 1] }

            // Control points derived from Catmull-Rom → cubic Bézier conversion
            val cp1x = p1.first + (p2.first - p0.first) * tension / 3f
            val cp1y = p1.second + (p2.second - p0.second) * tension / 3f
            val cp2x = p2.first - (p3.first - p1.first) * tension / 3f
            val cp2y = p2.second - (p3.second - p1.second) * tension / 3f

            sb.append("C ${cp1x.f()} ${cp1y.f()} ${cp2x.f()} ${cp2y.f()} ${p2.first.f()} ${p2.second.f()} ")
        }

        return sb.toString().trim()
    }

    private fun Float.f() = "%.2f".format(this)
}


