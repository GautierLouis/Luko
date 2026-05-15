package xyz.luko.designsystem.drawing

import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.unit.IntSize

/**
 * Provides [Matrix] transformations for rendering stroke-based drawings, such as SVG paths,
 * within a coordinate system.
 *
 * This class handles the mathematical operations required to flip axes, scale content
 * to fit a target canvas, and center drawings while maintaining their aspect ratio.
 *
 * @property viewBox The [SvgViewBox] defining the source coordinate system and dimensions.
 */
class StrokeMatrixProvider(
    private val viewBox: SvgViewBox = SvgViewBox(),
) {
    fun baseMatrix(): Matrix = Matrix().apply {
        scale(1f, -1f)
        translate(0f, -viewBox.flipY)
    }

    fun centerMatrix(canvasSize: IntSize, padding: Float = 0f): Matrix {
        val availableWidth = canvasSize.width - (padding * 2)
        val availableHeight = canvasSize.height - (padding * 2)
        val scale = minOf(availableWidth / viewBox.size, availableHeight / viewBox.size)
        val scaledSize = viewBox.size * scale
        val offsetX = (canvasSize.width - scaledSize) / 2f
        val offsetY = (canvasSize.height - scaledSize) / 2f

        return Matrix().apply {
            translate(offsetX, offsetY)
            scale(scale, scale)
        }
    }
}