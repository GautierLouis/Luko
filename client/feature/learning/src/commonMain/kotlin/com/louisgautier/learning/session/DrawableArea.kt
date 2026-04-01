package com.louisgautier.learning.session

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import com.louisgautier.designsystem.pointsToPath
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.learning.DrawableAreaDefault
import com.louisgautier.learning.drawDashedLineWithFilledArrow
import com.louisgautier.learning.drawStroke
import androidx.compose.ui.graphics.drawscope.Stroke as AndroidStroke

@Composable
internal fun DrawableArea(
    referenceStrokes: List<List<Offset>> = emptyList(),
    referenceHint: List<Offset> = emptyList(),
    previousDrawnStrokes: List<List<Offset>> = emptyList(),
    ongoingStroke: List<Offset> = emptyList(),
    modifier: Modifier = Modifier,
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
                color = indicationColor
            )
        }

        // Previous drawn strokes from user
        previousDrawnStrokes.forEach {
            val path = it.pointsToPath()
            drawPath(
                path = path,
                color = color,
                style = AndroidStroke(
                    width = DrawableAreaDefault.STROKE_WIDTH,
                    cap = StrokeCap.Round
                )
            )
        }

        // Ongoing path
        val livePath = ongoingStroke.pointsToPath()
        drawPath(
            path = livePath,
            color = color,
            style = AndroidStroke(
                width = DrawableAreaDefault.STROKE_WIDTH,
                cap = StrokeCap.Round
            )
        )
    }
}