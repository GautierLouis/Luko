package com.louisgautier.learning.drawing

import androidx.compose.ui.geometry.Offset

fun List<Offset>.toCatmullRomControlPoints(): List<CatmullRomControlPoint> {

    val controlPoints = mutableListOf<CatmullRomControlPoint>()

    for (i in 0 until this.size - 1) {
        val p0 = if (i > 0) this[i - 1] else this[i]
        val p1 = this[i]
        val p2 = this[i + 1]
        val p3 = if (i + 2 < this.size) this[i + 2] else p2

        val controlPoint1 = Offset(
            p1.x + (p2.x - p0.x) / 6f,
            p1.y + (p2.y - p0.y) / 6f
        )
        val controlPoint2 = Offset(
            p2.x - (p3.x - p1.x) / 6f,
            p2.y - (p3.y - p1.y) / 6f
        )

        val cp = CatmullRomControlPoint(
            controlPoint1.x, controlPoint1.y,
            controlPoint2.x, controlPoint2.y,
            p2.x, p2.y
        )

        controlPoints.add(cp)

    }

    return controlPoints.toList()
}