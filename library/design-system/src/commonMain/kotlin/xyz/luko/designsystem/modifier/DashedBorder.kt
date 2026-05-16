package xyz.luko.designsystem.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isSimple
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Dp

fun Modifier.dashedBorder(
    width: Dp,
    color: Color,
    shape: Shape = RectangleShape,
    on: Dp,
    off: Dp,
): Modifier =
    dashedBorder(
        width = width,
        brush = SolidColor(color),
        shape = shape,
        on = on,
        off = off,
    )

fun Modifier.dashedBorder(
    width: Dp,
    brush: Brush,
    shape: Shape = RectangleShape,
    on: Dp,
    off: Dp,
): Modifier =
    this.then(
        DashedBorderElement(
            width = width,
            brush = brush,
            shape = shape,
            on = on,
            off = off,
        ),
    )

private data class DashedBorderElement(
    val width: Dp,
    val brush: Brush,
    val shape: Shape,
    val on: Dp,
    val off: Dp,
) : ModifierNodeElement<DashedBorderNode>() {
    override fun create(): DashedBorderNode =
        DashedBorderNode(
            width = width,
            brush = brush,
            shape = shape,
            on = on,
            off = off,
        )

    override fun update(node: DashedBorderNode) {
        node.width = width
        node.brush = brush
        node.shape = shape
        node.on = on
        node.off = off
        node.invalidateDraw()
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "dashedBorder"
        properties["width"] = width
        properties["brush"] = brush
        properties["shape"] = shape
        properties["on"] = on
        properties["off"] = off
    }
}

private class DashedBorderNode(
    var width: Dp,
    var brush: Brush,
    var shape: Shape,
    var on: Dp,
    var off: Dp,
) : Modifier.Node(),
    DrawModifierNode {
    override fun ContentDrawScope.draw() {
        drawContent()

        val outline = shape.createOutline(size, layoutDirection, this)
        val borderSize = if (width == Dp.Hairline) 1f else width.toPx()

        if (borderSize <= 0f || size.minDimension <= 0f) return

        val dashEffect =
            PathEffect.dashPathEffect(
                floatArrayOf(on.toPx(), off.toPx()),
            )

        if (outline is Outline.Rectangle) {
            val stroke =
                Stroke(
                    width = borderSize,
                    pathEffect = dashEffect,
                )

            val half = stroke.width / 2

            drawRect(
                brush = brush,
                topLeft = Offset(half, half),
                size =
                    Size(
                        size.width - stroke.width,
                        size.height - stroke.width,
                    ),
                style = stroke,
            )
            return
        }

        val strokeWidth = 1.2f * borderSize
        val inset = borderSize - strokeWidth / 2

        val insetSize =
            Size(
                width = size.width - inset * 2,
                height = size.height - inset * 2,
            )

        val insetOutline =
            shape.createOutline(
                size = insetSize,
                layoutDirection = layoutDirection,
                density = this,
            )

        val stroke =
            Stroke(
                width = strokeWidth,
                pathEffect = dashEffect,
            )

        val outerPath =
            when (outline) {
                is Outline.Rounded ->
                    Path().apply {
                        addRoundRect(outline.roundRect)
                    }

                is Outline.Generic -> outline.path
            }

        val innerPath =
            when (insetOutline) {
                is Outline.Rounded -> {
                    if (insetOutline.roundRect.isSimple) {
                        null
                    } else {
                        Path().apply {
                            addRoundRect(insetOutline.roundRect)
                            translate(Offset(inset, inset))
                        }
                    }
                }

                is Outline.Generic ->
                    Path().apply {
                        addPath(insetOutline.path, Offset(inset, inset))
                    }

                else -> null
            }

        val isSimpleRoundRect =
            insetOutline is Outline.Rounded &&
                insetOutline.roundRect.isSimple

        withTransform({
            clipPath(outerPath)

            if (isSimpleRoundRect) {
                translate(inset, inset)
            }
        }) {
            when {
                isSimpleRoundRect -> {
                    val rr = insetOutline.roundRect

                    drawRoundRect(
                        brush = brush,
                        topLeft = Offset(rr.left, rr.top),
                        size = Size(rr.width, rr.height),
                        cornerRadius = rr.topLeftCornerRadius,
                        style = stroke,
                    )
                }

                innerPath != null -> {
                    drawPath(
                        path = innerPath,
                        brush = brush,
                        style = stroke,
                    )
                }
            }
        }

        clipRect {
            val hairline =
                Stroke(
                    width = Stroke.HairlineWidth,
                    pathEffect = dashEffect,
                )

            when (outline) {
                is Outline.Rounded -> {
                    val rr = outline.roundRect

                    drawRoundRect(
                        brush = brush,
                        topLeft = Offset(rr.left, rr.top),
                        size = Size(rr.width, rr.height),
                        cornerRadius = rr.topLeftCornerRadius,
                        style = hairline,
                    )
                }

                is Outline.Generic -> {
                    drawPath(
                        path = outerPath,
                        brush = brush,
                        style = hairline,
                    )
                }
            }
        }
    }
}
