package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.Lightbulb: ImageVector
    get() {
        if (_Lightbulb != null) {
            return _Lightbulb!!
        }
        _Lightbulb =
            ImageVector
                .Builder(
                    name = "Lightbulb",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(12f, 22f)
                        curveTo(11.45f, 22f, 10.979f, 21.804f, 10.587f, 21.413f)
                        curveTo(10.196f, 21.021f, 10f, 20.55f, 10f, 20f)
                        horizontalLineTo(14f)
                        curveTo(14f, 20.55f, 13.804f, 21.021f, 13.413f, 21.413f)
                        curveTo(13.021f, 21.804f, 12.55f, 22f, 12f, 22f)
                        close()
                        moveTo(8f, 19f)
                        verticalLineTo(17f)
                        horizontalLineTo(16f)
                        verticalLineTo(19f)
                        horizontalLineTo(8f)
                        close()
                        moveTo(8.25f, 16f)
                        curveTo(7.1f, 15.317f, 6.188f, 14.4f, 5.512f, 13.25f)
                        curveTo(4.838f, 12.1f, 4.5f, 10.85f, 4.5f, 9.5f)
                        curveTo(4.5f, 7.417f, 5.229f, 5.646f, 6.688f, 4.188f)
                        curveTo(8.146f, 2.729f, 9.917f, 2f, 12f, 2f)
                        curveTo(14.083f, 2f, 15.854f, 2.729f, 17.313f, 4.188f)
                        curveTo(18.771f, 5.646f, 19.5f, 7.417f, 19.5f, 9.5f)
                        curveTo(19.5f, 10.85f, 19.163f, 12.1f, 18.487f, 13.25f)
                        curveTo(17.813f, 14.4f, 16.9f, 15.317f, 15.75f, 16f)
                        horizontalLineTo(8.25f)
                        close()
                    }
                }.build()

        return _Lightbulb!!
    }

@Suppress("ObjectPropertyName")
private var _Lightbulb: ImageVector? = null
