package xyz.luko.learning.session.drawing

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import xyz.luko.designsystem.drawing.pointsToPath
import xyz.luko.designsystem.theme.Theme
import androidx.compose.ui.graphics.drawscope.Stroke as AndroidStroke

/**
 * Preview in Parent
 */
@Composable
internal fun DrawableArea(
    modifier: Modifier = Modifier,
    referenceStrokes: List<List<Offset>> = emptyList(),
    referenceHint: List<Offset> = emptyList(),
    previousDrawnStrokes: List<List<Offset>> = emptyList(),
    ongoingStroke: List<Offset> = emptyList(),
) {
    val color = Theme.materialColors.inverseSurface
    val referenceColor = Theme.materialColors.outlineVariant
    val indicationColor = Theme.materialColors.primary

    Canvas(modifier = modifier) {
        // Grey-out result as reference
        referenceStrokes.forEach { drawStroke(stroke = it, strokeColor = referenceColor) }

        // Current dashed-stroke to be drawn
        if (referenceHint.isNotEmpty()) {
            drawDashedLineWithFilledArrow(
                points = referenceHint,
                color = indicationColor,
            )
        }

        // Previous drawn strokes from user
        previousDrawnStrokes.forEach {
            val path = it.pointsToPath()
            drawPath(
                path = path,
                color = color,
                style =
                    AndroidStroke(
                        width = DrawableAreaDefault.STROKE_WIDTH,
                        cap = StrokeCap.Round,
                    ),
            )
        }

        // Ongoing path
        val livePath = ongoingStroke.pointsToPath()
        drawPath(
            path = livePath,
            color = color,
            style =
                AndroidStroke(
                    width = DrawableAreaDefault.STROKE_WIDTH,
                    cap = StrokeCap.Round,
                ),
        )
    }
}
