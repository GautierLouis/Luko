package xyz.luko.learning.drawing

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp

internal fun DrawScope.drawStroke(
    path: Path,
    color: Color,
    width: Dp
) {
    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = width.toPx(),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}

internal fun DrawScope.drawHint(
    path: Path,
    arrowHead: Path?,
    color: Color,
) {
    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = DrawableAreaDefault.STROKE_DASH_WIDTH.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(
                    DrawableAreaDefault.DASH_WIDTH.toPx(),
                    DrawableAreaDefault.DASH_GAP.toPx()
                ),
                phase = DrawableAreaDefault.DASH_PHASE.toPx(),
            ),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )

    )

    arrowHead?.let {
        drawPath(
            path = it,
            color = color,
            style = Stroke(
                width = DrawableAreaDefault.ARROW_SIZE.toPx(),
                cap = StrokeCap.Round,
            )
        )
    }
}
