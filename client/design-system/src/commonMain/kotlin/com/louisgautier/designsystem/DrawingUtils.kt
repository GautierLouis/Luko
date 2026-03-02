package com.louisgautier.designsystem

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure

fun PathEffect.Companion.dashEffect(scaleFactor: Float): PathEffect {
    val intervals = floatArrayOf(50f, 50f)
    val dashPatternPx =
        FloatArray(intervals.size) { i -> intervals[i] * scaleFactor }
    return PathEffect.dashPathEffect(dashPatternPx)
}

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

fun medianToPath(median: List<List<Double>>): String {
    return "M ${median.joinToString(" L ") { "${it[0]} ${it[1]}" }}"
}

fun Path.toOffset(): List<Offset> {
    val pathMeasure = PathMeasure()
    pathMeasure.setPath(this, false)

    val length = pathMeasure.length
    if (length == 0f) return emptyList()

    val mOffset = mutableListOf<Offset>()

    for (i in 0 until pathMeasure.length.toInt()) {
        val dist = (i.toFloat() / (pathMeasure.length - 1)) * length
        val pos = pathMeasure.getPosition(dist)
        mOffset.add(pos)
    }
    return mOffset
}