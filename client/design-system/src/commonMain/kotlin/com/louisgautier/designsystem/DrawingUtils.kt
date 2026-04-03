package com.louisgautier.designsystem

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