package xyz.luko.learning.session.drawing

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

internal fun DrawScope.drawHint(
    path: Path,
    arrowHead: Path?,
    color: Color,
) {
    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = DrawableAreaDefault.STROKE_HINT_WIDTH,
            pathEffect =
                PathEffect.dashPathEffect(
                    intervals = floatArrayOf(
                        DrawableAreaDefault.STROKE_HINT_DASH_WIDTH,
                        DrawableAreaDefault.STROKE_HINT_DASH_GAP
                    ),
                    phase = 50f,
                ),
            cap = StrokeCap.Round,
        )

    )

    arrowHead?.let {
        drawPath(
            path = it,
            color = color,
            style = Stroke(
                width = 15f,
                cap = StrokeCap.Round,
            )
        )
    }
}
