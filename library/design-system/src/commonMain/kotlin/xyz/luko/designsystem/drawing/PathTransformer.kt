package xyz.luko.designsystem.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.IntSize

/**
 * Transforms SVG path data strings into Compose [Path] objects, applying scaling and
 * positioning transformations to fit the paths within a specified canvas size.
 *
 * This class handles the conversion process by parsing SVG data, applying a base
 * transformation (normalization), and then centering the result within the target
 */
class PathTransformer(
    private val matrixProvider: StrokeMatrixProvider = StrokeMatrixProvider(),
) {
    fun transform(
        svgPathDataList: List<String>,
        canvasSize: IntSize,
        padding: Float = 0f,
    ): List<Path> {
        if (svgPathDataList.isEmpty()) return emptyList()

        val base = matrixProvider.baseMatrix()
        val center = matrixProvider.centerMatrix(canvasSize, padding)

        return svgPathDataList
            .map { PathParser().parsePathString(it).toPath().transformWith(base) }
            .map { it.transformWith(center) }
    }

    private fun Path.transformWith(matrix: Matrix): Path {
        val pathMeasure = PathMeasure()
        pathMeasure.setPath(this, false)
        val length = pathMeasure.length
        if (length == 0f) return Path()

        val offsets = mutableListOf<Offset>()
        for (i in 0 until length.toInt()) {
            val dist = (i.toFloat() / (length - 1)) * length
            offsets.add(matrix.map(pathMeasure.getPosition(dist)))
        }
        return offsets.pointsToPath()
    }
}