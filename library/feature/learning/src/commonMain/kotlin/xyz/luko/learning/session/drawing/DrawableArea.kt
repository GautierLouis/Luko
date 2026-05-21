package xyz.luko.learning.session.drawing

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import xyz.luko.baseui.drawing.TransformedHint
import xyz.luko.baseui.drawing.pointsToPath
import xyz.luko.designsystem.theme.Theme

/**
 * Preview in Parent
 */
@Composable
internal fun DrawableArea(
    modifier: Modifier = Modifier,
    referencePath: List<Path> = emptyList(),
    referenceHint: TransformedHint? = null,
    previousDrawnStrokes: List<List<Offset>> = emptyList(),
    ongoingStroke: List<Offset> = emptyList(),
) {
    val color = Theme.materialColors.inverseSurface
    val referenceColor = Theme.materialColors.outlineVariant
    val indicationColor = Theme.materialColors.primary

    val style = Stroke(
        width = DrawableAreaDefault.STROKE_WIDTH,
        cap = StrokeCap.Round,
        join = StrokeJoin.Round
    )

    Canvas(modifier = modifier) {
        referencePath.forEach { path ->
            drawPath(
                path = path,
                color = referenceColor,
                style = style,
            )
        }

        if (referenceHint != null) {
            drawHint(
                path = referenceHint.strokePath,
                arrowHead = referenceHint.arrowHead,
                color = indicationColor
            )
        }

        previousDrawnStrokes.forEach {
            val path = it.pointsToPath()
            drawPath(
                path = path,
                color = color,
                style = style,
            )
        }

        val livePath = ongoingStroke.pointsToPath()
        drawPath(
            path = livePath,
            color = color,
            style = style,
        )
    }
}
