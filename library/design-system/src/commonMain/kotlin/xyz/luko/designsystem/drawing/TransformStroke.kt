package xyz.luko.designsystem.drawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

object TransformStroke {
    private val matrixProvider = StrokeMatrixProvider()
    private val offsetTransformer = OffsetTransformer(matrixProvider)
    private val pathTransformer = PathTransformer(matrixProvider)

    fun projectToCanvas(offset: List<Offset>, canvasSize: IntSize) =
        offsetTransformer.transform(offset, canvasSize)

    fun unprojectFromCanvas(offset: List<Offset>, canvasSize: IntSize) =
        offsetTransformer.inverse(offset, canvasSize)

    fun projectToCanvas(svgPathDataList: List<String>, canvasSize: IntSize, padding: Float = 0f) =
        pathTransformer.transform(svgPathDataList, canvasSize, padding)
}