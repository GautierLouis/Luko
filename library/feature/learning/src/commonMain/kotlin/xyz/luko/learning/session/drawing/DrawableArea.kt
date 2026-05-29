package xyz.luko.learning.session.drawing

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
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

    Canvas(modifier = modifier) {
        referencePath.forEach { path ->
            drawStroke(
                path = path,
                color = referenceColor,
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
            drawStroke(
                path = path,
                color = color,
            )
        }

        val livePath = ongoingStroke.pointsToPath()
        drawStroke(
            path = livePath,
            color = color,
        )
    }
}
