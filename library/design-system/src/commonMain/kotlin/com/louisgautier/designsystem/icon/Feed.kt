package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.Feed: ImageVector
    get() {
        if (_Feed != null) {
            return _Feed!!
        }
        _Feed = ImageVector.Builder(
            name = "Feed",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(4f, 21f)
                curveTo(3.45f, 21f, 2.979f, 20.804f, 2.588f, 20.413f)
                curveTo(2.196f, 20.021f, 2f, 19.55f, 2f, 19f)
                verticalLineTo(13f)
                curveTo(2f, 12.717f, 2.096f, 12.479f, 2.287f, 12.288f)
                curveTo(2.479f, 12.096f, 2.717f, 12f, 3f, 12f)
                curveTo(3.283f, 12f, 3.521f, 12.096f, 3.713f, 12.288f)
                curveTo(3.904f, 12.479f, 4f, 12.717f, 4f, 13f)
                verticalLineTo(19f)
                horizontalLineTo(12f)
                curveTo(12.283f, 19f, 12.521f, 19.096f, 12.712f, 19.288f)
                curveTo(12.904f, 19.479f, 13f, 19.717f, 13f, 20f)
                curveTo(13f, 20.283f, 12.904f, 20.521f, 12.712f, 20.712f)
                curveTo(12.521f, 20.904f, 12.283f, 21f, 12f, 21f)
                horizontalLineTo(4f)
                close()
                moveTo(8f, 17f)
                curveTo(7.45f, 17f, 6.979f, 16.804f, 6.588f, 16.413f)
                curveTo(6.196f, 16.021f, 6f, 15.55f, 6f, 15f)
                verticalLineTo(9f)
                curveTo(6f, 8.717f, 6.096f, 8.479f, 6.287f, 8.288f)
                curveTo(6.479f, 8.096f, 6.717f, 8f, 7f, 8f)
                curveTo(7.283f, 8f, 7.521f, 8.096f, 7.713f, 8.288f)
                curveTo(7.904f, 8.479f, 8f, 8.717f, 8f, 9f)
                verticalLineTo(15f)
                horizontalLineTo(16f)
                curveTo(16.283f, 15f, 16.521f, 15.096f, 16.712f, 15.288f)
                curveTo(16.904f, 15.479f, 17f, 15.717f, 17f, 16f)
                curveTo(17f, 16.283f, 16.904f, 16.521f, 16.712f, 16.712f)
                curveTo(16.521f, 16.904f, 16.283f, 17f, 16f, 17f)
                horizontalLineTo(8f)
                close()
                moveTo(12f, 13f)
                curveTo(11.45f, 13f, 10.979f, 12.804f, 10.587f, 12.413f)
                curveTo(10.196f, 12.021f, 10f, 11.55f, 10f, 11f)
                verticalLineTo(5f)
                curveTo(10f, 4.45f, 10.196f, 3.979f, 10.587f, 3.588f)
                curveTo(10.979f, 3.196f, 11.45f, 3f, 12f, 3f)
                horizontalLineTo(20f)
                curveTo(20.55f, 3f, 21.021f, 3.196f, 21.413f, 3.588f)
                curveTo(21.804f, 3.979f, 22f, 4.45f, 22f, 5f)
                verticalLineTo(11f)
                curveTo(22f, 11.55f, 21.804f, 12.021f, 21.413f, 12.413f)
                curveTo(21.021f, 12.804f, 20.55f, 13f, 20f, 13f)
                horizontalLineTo(12f)
                close()
                moveTo(12f, 11f)
                horizontalLineTo(20f)
                verticalLineTo(7f)
                horizontalLineTo(12f)
                verticalLineTo(11f)
                close()
            }
        }.build()

        return _Feed!!
    }

@Suppress("ObjectPropertyName")
private var _Feed: ImageVector? = null
