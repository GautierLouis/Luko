package com.louisgautier.designsystem

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.IntSize

/**
 * By default, strokes receive from API need to be rotated and scaled to fit correctly into a Canvas
 * Also ued to transform user's stoke to compare it with the reference
 */
object TransformStroke {
    private val matrix = Matrix().apply {
        scale(1f, -1f)
        translate(0f, -900f)
    }

    fun transformOffset(
        offset: List<Offset>,
        canvasSize: IntSize,
    ): List<Offset> {
        val centerMatrix = centerMatrix(canvasSize)

        return offset.map {
            centerMatrix.map(matrix.map(it))
        }
    }

    fun transformPath(
        svgPathDataList: List<String>,
        canvasSize: IntSize,
        padding: Float = 0f
    ): List<Path> {
        if (svgPathDataList.isEmpty()) return emptyList()

        val centerMatrix = centerMatrix(canvasSize, padding)
        val transformedPaths = svgPathDataList.map { pathData ->
            val path = PathParser().parsePathString(pathData).toPath()
            path.transformWith(matrix)
        }

        return transformedPaths.map { it.transformWith(centerMatrix) }
    }

    private fun centerMatrix(
        canvasSize: IntSize,
        padding: Float = 0f
    ): Matrix {
        // Use the original SVG viewBox size (1024x1024) for consistent aspect ratio
        val svgViewBoxSize = 1024f

        // Calculate available space (canvas minus padding)
        val availableWidth = canvasSize.width - (padding * 2)
        val availableHeight = canvasSize.height - (padding * 2)

        // Calculate scale to fit 1024x1024 viewBox within canvas
        val scale = minOf(availableWidth / svgViewBoxSize, availableHeight / svgViewBoxSize)

        // Calculate centered position based on 1024x1024 viewBox
        val scaledSize = svgViewBoxSize * scale
        val offsetX = (canvasSize.width - scaledSize) / 2f
        val offsetY = (canvasSize.height - scaledSize) / 2f

        // Apply centering to all paths
        return Matrix().apply {
            translate(offsetX, offsetY)
            scale(scale, scale)
        }
    }

    private fun Path.transformWith(matrix: Matrix): Path {
        val pathMeasure = PathMeasure()
        pathMeasure.setPath(this, false)

        val length = pathMeasure.length
        if (length == 0f) return Path()

        val mOffset = mutableListOf<Offset>()

        for (i in 0 until pathMeasure.length.toInt()) {
            val dist = (i.toFloat() / (pathMeasure.length - 1)) * length
            val pos = pathMeasure.getPosition(dist)
            val transformed = matrix.map(pos)
            mOffset.add(transformed)
        }

        return mOffset.pointsToPath()
    }
}