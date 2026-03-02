package com.louisgautier.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Graphic(
    val code: Int,
    val strokes: List<String>,
    val medians: List<Stroke>
) {
    val character: Char
        get() = Char(code)
}

@Serializable
data class Stroke(
    val points: List<Point>
)

@Serializable
data class Point(
    val x: Float,
    val y: Float
)
