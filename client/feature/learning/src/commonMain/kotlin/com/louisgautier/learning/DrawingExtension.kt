package com.louisgautier.learning

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.ai.Gray200
import com.louisgautier.learning.drawing.toCatmullRomControlPoints
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * Draw filled arrow head (alternative style)
 */
fun DrawScope.drawDashedLineWithFilledArrow(
    points: List<Offset>,
    color: Color = DrawableAreaDefault.STROKE_HINT_COLOR,
    dashWidth: Float = DrawableAreaDefault.STROKE_HINT_DASH_WIDTH,
    dashGap: Float = DrawableAreaDefault.STROKE_HINT_DASH_GAP,
    strokeWidth: Float = DrawableAreaDefault.STROKE_HINT_WIDTH,
    arrowHeadSize: Float = DrawableAreaDefault.STROKE_HINT_ARROW_HEAD_SIZE
) {

    // Create and draw dashed path
    val path = Path().apply {
        moveTo(points[0].x, points[0].y)
        points.drop(1).forEach { point ->
            lineTo(point.x, point.y)
        }
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = strokeWidth,
            pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(dashWidth, dashGap),
                phase = 50f
            ),
            cap = StrokeCap.Round,
        )
    )

    // Draw filled arrow head
    val lastPoint = points[points.size - 1]
    val secondLastPoint = points[points.size - 2]

    val angle = atan2(
        lastPoint.y - secondLastPoint.y,
        lastPoint.x - secondLastPoint.x
    )

    val arrowPath = Path().apply {
        moveTo(lastPoint.x, lastPoint.y)

        val wingAngle1 = angle + PI.toFloat() - 0.5f
        val wingAngle2 = angle + PI.toFloat() + 0.5f

        lineTo(
            lastPoint.x + arrowHeadSize * cos(wingAngle1),
            lastPoint.y + arrowHeadSize * sin(wingAngle1)
        )
        lineTo(
            lastPoint.x + arrowHeadSize * cos(wingAngle2),
            lastPoint.y + arrowHeadSize * sin(wingAngle2)
        )
        close()
    }

    // Fill the arrow
    drawPath(
        path = arrowPath,
        color = color,
        style = Stroke(
            width = 15f,
            cap = StrokeCap.Round
        )
    )
}

fun DrawScope.drawNumberedStartPoint(
    index: Int,
    textMeasurer: TextMeasurer,
    stroke: List<Offset>
) {
    val text = index.inc().toString()
    val style = TextStyle(
        color = Color.White,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold
    )
    val textLayoutResult = textMeasurer.measure(text, style)
    val textSize = textLayoutResult.size

    drawText(
        textMeasurer = textMeasurer,
        text = text,
        topLeft = Offset(
            stroke[0].x - textSize.width / 2,
            stroke[0].y - textSize.height / 2
        ),
        style = style
    )
}

/**
 * Helper function to draw the first point of the stroke.
 */
fun DrawScope.drawFirstPoint(point: Offset) {
    drawPoints(
        listOf(point),
        color = Gray200,
        strokeWidth = 50f,
        cap = StrokeCap.Round,
        pointMode = PointMode.Points,
    )
}

fun DrawScope.drawStroke(
    stroke: List<Offset>,
    strokeColor: Color = DrawableAreaDefault.STROKE_REFERENCE_COLOR,
    strokeWidth: Float = DrawableAreaDefault.STROKE_WIDTH,
) {
    val curvedStroke = stroke.toCatmullRomControlPoints()

    val path = Path()
    path.moveTo(curvedStroke[0].x3, curvedStroke[0].y3)

    curvedStroke.forEach { cp ->
        path.cubicTo(
            cp.x1, cp.y1,
            cp.x2, cp.y2,
            cp.x3, cp.y3
        )
    }
    drawPath(
        path = path,
        color = strokeColor,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        )
    )
}

private fun DrawScope.drawArrowDirection(
    stroke: List<Offset>,
    arrowDirection: Painter
) {
    val iconSize = 80f
    val half = -iconSize / 2
    val iconDefaultRotation = 90f

    val start = stroke[0]
    val next = stroke[1]
    val angle = atan2(next.y - start.y, next.x - start.x)
    val degrees = (angle * (180.0 / PI)) - iconDefaultRotation

    with(arrowDirection) {
        withTransform(
            transformBlock = {
                rotate(degrees.toFloat(), start)
                translate(left = start.x, top = start.y)
                translate(left = half, top = half)
            },
            drawBlock = {
                draw(size = Size(iconSize, iconSize))
            }
        )
    }
}