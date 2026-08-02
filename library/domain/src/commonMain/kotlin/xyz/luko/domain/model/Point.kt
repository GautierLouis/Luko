package xyz.luko.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Point {
    abstract val x: Float
    abstract val y: Float
    abstract val timestamp: Long

    abstract fun flipY(viewBoxSize: Float): Point

    @Serializable
    data class Straight(
        override val x: Float,
        override val y: Float,
        override val timestamp: Long = 0L
    ) : Point() {
        override fun flipY(viewBoxSize: Float) = copy(y = viewBoxSize - y)
    }

    @Serializable
    data class Curved(
        override val x: Float,
        override val y: Float,
        val cp1x: Float,
        val cp1y: Float,
        val cp2x: Float,
        val cp2y: Float,
        override val timestamp: Long = 0L,
    ) : Point() {
        override fun flipY(viewBoxSize: Float) = copy(
            y = viewBoxSize - y,
            cp1y = viewBoxSize - cp1y,
            cp2y = viewBoxSize - cp2y,
        )
    }
}

fun Stroke.toGlyphSpace(viewBoxSize: Float = 1024f): Stroke =
    copy(points = points.map { it.flipY(viewBoxSize) })
