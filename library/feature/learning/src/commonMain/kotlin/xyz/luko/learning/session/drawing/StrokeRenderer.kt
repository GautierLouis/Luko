package xyz.luko.learning.session.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import xyz.luko.designsystem.drawing.toCatmullRomControlPoints
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * Draw filled arrow head (alternative style)
 */
internal fun DrawScope.drawDashedLineWithFilledArrow(
    points: List<Offset>,
    color: Color,
    dashWidth: Float = DrawableAreaDefault.STROKE_HINT_DASH_WIDTH,
    dashGap: Float = DrawableAreaDefault.STROKE_HINT_DASH_GAP,
    strokeWidth: Float = DrawableAreaDefault.STROKE_HINT_WIDTH,
    arrowHeadSize: Float = DrawableAreaDefault.STROKE_HINT_ARROW_HEAD_SIZE,
) {
    // Create and draw dashed path
    val path =
        Path().apply {
            moveTo(points[0].x, points[0].y)
            points.drop(1).forEach { point ->
                lineTo(point.x, point.y)
            }
        }

    drawPath(
        path = path,
        color = color,
        style =
            Stroke(
                width = strokeWidth,
                pathEffect =
                    PathEffect.dashPathEffect(
                        intervals = floatArrayOf(dashWidth, dashGap),
                        phase = 50f,
                    ),
                cap = StrokeCap.Round,
            ),
    )

    // Draw filled arrow head
    val lastPoint = points[points.size - 1]
    val secondLastPoint = points[points.size - 2]

    val angle =
        atan2(
            lastPoint.y - secondLastPoint.y,
            lastPoint.x - secondLastPoint.x,
        )

    val arrowPath =
        Path().apply {
            moveTo(lastPoint.x, lastPoint.y)

            val wingAngle1 = angle + PI.toFloat() - 0.5f
            val wingAngle2 = angle + PI.toFloat() + 0.5f

            lineTo(
                lastPoint.x + arrowHeadSize * cos(wingAngle1),
                lastPoint.y + arrowHeadSize * sin(wingAngle1),
            )
            lineTo(
                lastPoint.x + arrowHeadSize * cos(wingAngle2),
                lastPoint.y + arrowHeadSize * sin(wingAngle2),
            )
            close()
        }

    // Fill the arrow
    drawPath(
        path = arrowPath,
        color = color,
        style =
            Stroke(
                width = 15f,
                cap = StrokeCap.Round,
            ),
    )
}

internal fun DrawScope.drawStroke(
    stroke: List<Offset>,
    strokeColor: Color,
    strokeWidth: Float = DrawableAreaDefault.STROKE_WIDTH,
) {
    val curvedStroke = stroke.toCatmullRomControlPoints()

    val path = Path()
    path.moveTo(curvedStroke[0].x3, curvedStroke[0].y3)

    curvedStroke.forEach { cp ->
        path.cubicTo(
            cp.x1,
            cp.y1,
            cp.x2,
            cp.y2,
            cp.x3,
            cp.y3,
        )
    }
    drawPath(
        path = path,
        color = strokeColor,
        style =
            Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round,
            ),
    )
}
