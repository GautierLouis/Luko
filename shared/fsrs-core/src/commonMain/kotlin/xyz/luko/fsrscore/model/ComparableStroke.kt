package xyz.luko.fsrscore.model

internal data class ComparableStroke(val points: List<ComparablePoint>)

internal data class ComparablePoint(val x: Float, val y: Float) {
    operator fun minus(other: ComparablePoint): ComparablePoint {
        return ComparablePoint(x - other.x, y - other.y)
    }
}
