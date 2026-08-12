package xyz.luko.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Point {
    abstract val x: Float
    abstract val y: Float
    abstract val timestamp: Long


    @Serializable
    data class Straight(
        override val x: Float,
        override val y: Float,
        override val timestamp: Long = 0L
    ) : Point()

    @Serializable
    data class Curved(
        override val x: Float,
        override val y: Float,
        val cp1x: Float,
        val cp1y: Float,
        val cp2x: Float,
        val cp2y: Float,
        override val timestamp: Long = 0L,
    ) : Point()
}
