package xyz.luko.ui.drawing

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import xyz.luko.designsystem.theme.Theme
import xyz.luko.domain.model.Stroke
import xyz.luko.ui.drawing.internal.StrokeTransformer
import xyz.luko.ui.drawing.internal.drawHint
import xyz.luko.ui.drawing.internal.drawStroke
import xyz.luko.ui.drawing.internal.drawingDetector
import xyz.luko.ui.drawing.internal.model.TransformedHint
import xyz.luko.ui.drawing.internal.pointsToPath

/**
 * Preview in Parent
 */
@Composable
fun DrawableArea(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = DrawableAreaDefault.STROKE_WIDTH,
    enableDrawing: Boolean = true,
    reference: List<Stroke> = emptyList(),
    hint: Stroke? = null,
    userStroke: List<Stroke> = emptyList(),
    onDraw: (Stroke) -> Unit = {},
) {

    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    val ongoingStroke = remember { mutableStateListOf<Offset>() }
    val transformer = remember { StrokeTransformer() }

    val referencePaths = remember(reference, canvasSize) {
        reference.map { transformer.toCanvasPath(it, canvasSize) }
    }

    val referenceHint = remember(hint, canvasSize) {
        hint?.let { transformer.toCanvasHint(it, canvasSize) }
    }

    val previousUserDraw = remember(userStroke, canvasSize) {
        userStroke.map { transformer.toCanvasPath(it, canvasSize) }
    }

    val drawingModifier = if (enableDrawing) {
        Modifier.drawingDetector(
            points = ongoingStroke,
            onGestureComplete = {
                val raw = transformer.fromCanvasOffsets(ongoingStroke.toList(), canvasSize)
                onDraw(raw)
                ongoingStroke.clear()
            },
        )
    } else Modifier

    DrawableArea(
        strokeWidth = strokeWidth,
        referencePath = referencePaths,
        referenceHint = referenceHint,
        previousDrawnStrokes = previousUserDraw,
        ongoingStroke = ongoingStroke,
        modifier = modifier
            .onGloballyPositioned { canvasSize = it.size }
            .then(drawingModifier)
    )
}


@Composable
private fun DrawableArea(
    strokeWidth: Dp,
    modifier: Modifier = Modifier,
    referencePath: List<Path> = emptyList(),
    referenceHint: TransformedHint? = null,
    previousDrawnStrokes: List<Path> = emptyList(),
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
                width = strokeWidth
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
            drawStroke(
                path = it,
                color = color,
                width = strokeWidth,
            )
        }

        val livePath = ongoingStroke.pointsToPath()
        drawStroke(
            path = livePath,
            color = color,
            width = strokeWidth
        )
    }
}
