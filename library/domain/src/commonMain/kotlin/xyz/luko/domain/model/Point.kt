package xyz.luko.domain.model

sealed class Point {

    abstract val x: Float
    abstract val y: Float

    data class Straight(
        override val x: Float,
        override val y: Float
    ) : Point()

    data class Curved(
        override val x: Float,
        override val y: Float,
        val cp1x: Float,
        val cp1y: Float,
        val cp2x: Float,
        val cp2y: Float
    ) : Point()
}
