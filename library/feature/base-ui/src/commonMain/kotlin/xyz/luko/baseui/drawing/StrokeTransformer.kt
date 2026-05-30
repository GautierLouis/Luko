package xyz.luko.baseui.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.IntSize
import xyz.luko.domain.model.Point
import xyz.luko.domain.model.Stroke
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * Responsible for transforming drawing data—such as SVG paths, [Stroke] objects, and coordinate offsets—between
 * coordinate systems.
 *
 * This class uses [StrokeMatrixProvider] to apply base transformations and centering logic, ensuring that
 * drawing elements are correctly scaled, positioned, and oriented for display on a Compose Canvas.
 *
 * @property matrixProvider Provides the transformation matrices used to map points from their source
 * coordinate space to the canvas space.
 */
class StrokeTransformer(
    private val matrixProvider: StrokeMatrixProvider = StrokeMatrixProvider(),
) {
    fun toCanvasPaths(
        svgPaths: List<String>,
        canvasSize: IntSize,
        padding: Float = 0f,
    ): List<Path> {
        if (svgPaths.isEmpty()) return emptyList()
        val base = matrixProvider.baseMatrix()
        val center = matrixProvider.centerMatrix(canvasSize, padding)
        return svgPaths.map {
            PathParser().parsePathString(it).toPath()
                .apply { transform(base) }
                .apply { transform(center) }
        }
    }

    fun toCanvasPath(
        stroke: Stroke,
        canvasSize: IntSize,
        padding: Float = 0f,
    ): Path {
        val base = matrixProvider.baseMatrix()
        val center = matrixProvider.centerMatrix(canvasSize, padding)
        return stroke.toPath(base, center)
    }

    fun toCanvasHint(
        stroke: Stroke,
        canvasSize: IntSize,
        padding: Float = 0f,
    ): TransformedHint {
        val base = matrixProvider.baseMatrix()
        val center = matrixProvider.centerMatrix(canvasSize, padding)
        return stroke.toHint(base, center)
    }

    fun toCanvasOffsets(
        offsets: List<Offset>,
        canvasSize: IntSize,
    ): List<Offset> {
        val base = matrixProvider.baseMatrix()
        val center = matrixProvider.centerMatrix(canvasSize)
        return offsets.map { center.map(base.map(it)) }
    }

    fun fromCanvasOffsets(
        offsets: List<Offset>,
        canvasSize: IntSize,
    ): Stroke {
        val invertedCenter = matrixProvider.centerMatrix(canvasSize).apply { invert() }
        val invertedBase = matrixProvider.baseMatrix().apply { invert() }
        return Stroke(
            points = offsets.map {
                val mapped = invertedBase.map(invertedCenter.map(it))
                Point.Straight(mapped.x, mapped.y)
            }
        )
    }

    private fun Stroke.toPath(base: Matrix, center: Matrix): Path = Path().apply {
        points.forEachIndexed { i, point ->
            val anchor = center.map(base.map(Offset(point.x, point.y)))
            when {
                i == 0 -> moveTo(anchor.x, anchor.y)
                point is Point.Straight -> lineTo(anchor.x, anchor.y)
                else -> {
                    val p = point as Point.Curved
                    val cp1 = center.map(base.map(Offset(p.cp1x, p.cp1y)))
                    val cp2 = center.map(base.map(Offset(p.cp2x, p.cp2y)))
                    cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, anchor.x, anchor.y)
                }
            }
        }
    }

    private fun Stroke.toHint(base: Matrix, center: Matrix): TransformedHint {
        val transformedPoints = points.map { point ->
            when (point) {
                is Point.Straight -> Point.Straight(
                    x = center.map(base.map(Offset(point.x, point.y))).x,
                    y = center.map(base.map(Offset(point.x, point.y))).y,
                )

                is Point.Curved -> Point.Curved(
                    x = center.map(base.map(Offset(point.x, point.y))).x,
                    y = center.map(base.map(Offset(point.x, point.y))).y,
                    cp1x = center.map(base.map(Offset(point.cp1x, point.cp1y))).x,
                    cp1y = center.map(base.map(Offset(point.cp1x, point.cp1y))).y,
                    cp2x = center.map(base.map(Offset(point.cp2x, point.cp2y))).x,
                    cp2y = center.map(base.map(Offset(point.cp2x, point.cp2y))).y,
                )
            }
        }
        return TransformedHint(
            strokePath = toPath(base, center),
            arrowHead = buildArrowHead(transformedPoints),
        )
    }

    private fun buildArrowHead(points: List<Point>): Path? {
        if (points.size < 2) return null
        val last = Offset(points.last().x, points.last().y)
        val secondLast = Offset(points[points.size - 2].x, points[points.size - 2].y)
        val angle = atan2(last.y - secondLast.y, last.x - secondLast.x)
        return Path().apply {
            moveTo(last.x, last.y)
            val wing1 = angle + PI.toFloat() - 0.5f
            val wing2 = angle + PI.toFloat() + 0.5f
            lineTo(last.x + 10f * cos(wing1), last.y + 10f * sin(wing1))
            lineTo(last.x + 10f * cos(wing2), last.y + 10f * sin(wing2))
            close()
        }
    }
}
