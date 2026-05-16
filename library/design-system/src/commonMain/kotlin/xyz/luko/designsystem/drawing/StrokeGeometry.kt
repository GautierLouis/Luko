package xyz.luko.designsystem.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path

fun List<Offset>.pointsToPath(): Path {
    val path = Path()
    if (isEmpty()) return path
    path.moveTo(get(0).x, get(0).y)
    if (size == 1) {
        val p = get(0)
        path.addOval(Rect(p.x - 1f, p.y - 1f, p.x + 1f, p.y + 1f))
        return path
    }
    var prev = get(0)
    for (i in 1 until size) {
        val curr = get(i)
        val midX = (prev.x + curr.x) / 2f
        val midY = (prev.y + curr.y) / 2f
        path.quadraticTo(prev.x, prev.y, midX, midY)
        prev = curr
    }
    val last = last()
    path.lineTo(last.x, last.y)
    return path
}

fun List<Offset>.toCatmullRomControlPoints(): List<CatmullRomControlPoint> {
    val controlPoints = mutableListOf<CatmullRomControlPoint>()

    for (i in 0 until this.size - 1) {
        val p0 = if (i > 0) this[i - 1] else this[i]
        val p1 = this[i]
        val p2 = this[i + 1]
        val p3 = if (i + 2 < this.size) this[i + 2] else p2

        val controlPoint1 =
            Offset(
                p1.x + (p2.x - p0.x) / 6f,
                p1.y + (p2.y - p0.y) / 6f,
            )
        val controlPoint2 =
            Offset(
                p2.x - (p3.x - p1.x) / 6f,
                p2.y - (p3.y - p1.y) / 6f,
            )

        val cp =
            CatmullRomControlPoint(
                controlPoint1.x,
                controlPoint1.y,
                controlPoint2.x,
                controlPoint2.y,
                p2.x,
                p2.y,
            )

        controlPoints.add(cp)
    }

    return controlPoints.toList()
}
