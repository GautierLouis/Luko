package xyz.luko.designsystem.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

/**
 * Responsible for applying and reversing coordinate transformations on lists of [Offset] points.
 *
 * This class uses a [StrokeMatrixProvider] to calculate transformations, such as scaling or
 * centering, allowing coordinate data to be adapted to a specific [IntSize] canvas.
 *
 * @property matrixProvider The provider used to retrieve base and centering transformation matrices.
 */
class OffsetTransformer(
    private val matrixProvider: StrokeMatrixProvider = StrokeMatrixProvider(),
) {
    fun transform(offset: List<Offset>, canvasSize: IntSize): List<Offset> {
        val base = matrixProvider.baseMatrix()
        val center = matrixProvider.centerMatrix(canvasSize)
        return offset.map { center.map(base.map(it)) }
    }

    fun inverse(offset: List<Offset>, canvasSize: IntSize): List<Offset> {
        val invertedCenter = matrixProvider.centerMatrix(canvasSize).apply { invert() }
        val invertedBase = matrixProvider.baseMatrix().apply { invert() }
        return offset.map { invertedBase.map(invertedCenter.map(it)) }
    }
}