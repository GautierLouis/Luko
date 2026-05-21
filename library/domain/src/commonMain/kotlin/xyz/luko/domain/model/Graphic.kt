package xyz.luko.domain.model

data class Graphic(
    val code: Int,
    val strokes: List<String>,
    val medians: List<Stroke>,
    val smoothMedians: List<Stroke>,
)

data class Stroke(
    val points: List<Point>,
)

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
