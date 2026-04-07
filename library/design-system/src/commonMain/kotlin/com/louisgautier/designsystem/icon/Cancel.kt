package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.Cancel: ImageVector
    get() {
        if (_Cancel != null) {
            return _Cancel!!
        }
        _Cancel = ImageVector.Builder(
            name = "Cancel",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 13.4f)
                lineTo(14.9f, 16.3f)
                curveTo(15.083f, 16.483f, 15.317f, 16.575f, 15.6f, 16.575f)
                curveTo(15.883f, 16.575f, 16.117f, 16.483f, 16.3f, 16.3f)
                curveTo(16.483f, 16.117f, 16.575f, 15.883f, 16.575f, 15.6f)
                curveTo(16.575f, 15.317f, 16.483f, 15.083f, 16.3f, 14.9f)
                lineTo(13.4f, 12f)
                lineTo(16.3f, 9.1f)
                curveTo(16.483f, 8.917f, 16.575f, 8.683f, 16.575f, 8.4f)
                curveTo(16.575f, 8.117f, 16.483f, 7.883f, 16.3f, 7.7f)
                curveTo(16.117f, 7.517f, 15.883f, 7.425f, 15.6f, 7.425f)
                curveTo(15.317f, 7.425f, 15.083f, 7.517f, 14.9f, 7.7f)
                lineTo(12f, 10.6f)
                lineTo(9.1f, 7.7f)
                curveTo(8.917f, 7.517f, 8.683f, 7.425f, 8.4f, 7.425f)
                curveTo(8.117f, 7.425f, 7.883f, 7.517f, 7.7f, 7.7f)
                curveTo(7.517f, 7.883f, 7.425f, 8.117f, 7.425f, 8.4f)
                curveTo(7.425f, 8.683f, 7.517f, 8.917f, 7.7f, 9.1f)
                lineTo(10.6f, 12f)
                lineTo(7.7f, 14.9f)
                curveTo(7.517f, 15.083f, 7.425f, 15.317f, 7.425f, 15.6f)
                curveTo(7.425f, 15.883f, 7.517f, 16.117f, 7.7f, 16.3f)
                curveTo(7.883f, 16.483f, 8.117f, 16.575f, 8.4f, 16.575f)
                curveTo(8.683f, 16.575f, 8.917f, 16.483f, 9.1f, 16.3f)
                lineTo(12f, 13.4f)
                close()
                moveTo(12f, 22f)
                curveTo(10.617f, 22f, 9.317f, 21.737f, 8.1f, 21.212f)
                curveTo(6.883f, 20.688f, 5.825f, 19.975f, 4.925f, 19.075f)
                curveTo(4.025f, 18.175f, 3.313f, 17.117f, 2.787f, 15.9f)
                curveTo(2.263f, 14.683f, 2f, 13.383f, 2f, 12f)
                curveTo(2f, 10.617f, 2.263f, 9.317f, 2.787f, 8.1f)
                curveTo(3.313f, 6.883f, 4.025f, 5.825f, 4.925f, 4.925f)
                curveTo(5.825f, 4.025f, 6.883f, 3.313f, 8.1f, 2.787f)
                curveTo(9.317f, 2.263f, 10.617f, 2f, 12f, 2f)
                curveTo(13.383f, 2f, 14.683f, 2.263f, 15.9f, 2.787f)
                curveTo(17.117f, 3.313f, 18.175f, 4.025f, 19.075f, 4.925f)
                curveTo(19.975f, 5.825f, 20.688f, 6.883f, 21.212f, 8.1f)
                curveTo(21.737f, 9.317f, 22f, 10.617f, 22f, 12f)
                curveTo(22f, 13.383f, 21.737f, 14.683f, 21.212f, 15.9f)
                curveTo(20.688f, 17.117f, 19.975f, 18.175f, 19.075f, 19.075f)
                curveTo(18.175f, 19.975f, 17.117f, 20.688f, 15.9f, 21.212f)
                curveTo(14.683f, 21.737f, 13.383f, 22f, 12f, 22f)
                close()
            }
        }.build()

        return _Cancel!!
    }

@Suppress("ObjectPropertyName")
private var _Cancel: ImageVector? = null
