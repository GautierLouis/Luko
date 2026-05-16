package xyz.luko.designsystem.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.IntSize

object TransformStroke {
    private val matrixProvider = StrokeMatrixProvider()
    private val offsetTransformer = OffsetTransformer(matrixProvider)
    private val pathTransformer = PathTransformer(matrixProvider)

    fun projectToCanvas(
        offset: List<Offset>,
        canvasSize: IntSize,
    ): List<Offset> = offsetTransformer.transform(offset, canvasSize)

    fun unprojectFromCanvas(
        offset: List<Offset>,
        canvasSize: IntSize,
    ): List<Offset> = offsetTransformer.inverse(offset, canvasSize)

    fun projectToCanvas(
        svgPathDataList: List<String>,
        canvasSize: IntSize,
        padding: Float = 0f,
    ): List<Path> = pathTransformer.transform(svgPathDataList, canvasSize, padding)
}
