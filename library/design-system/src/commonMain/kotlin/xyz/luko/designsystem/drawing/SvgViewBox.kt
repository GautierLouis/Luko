package xyz.luko.designsystem.drawing

// Holds the SVG coordinate space constants
// Testable: no Android deps, pure math
data class SvgViewBox(
    val size: Float = 1024f,
    val flipY: Float = 900f,
)