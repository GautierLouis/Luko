package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.Visibility: ImageVector
    get() {
        if (_Visibility != null) {
            return _Visibility!!
        }
        _Visibility =
            ImageVector
                .Builder(
                    name = "Visibility",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(12f, 16f)
                        curveTo(13.25f, 16f, 14.313f, 15.563f, 15.188f, 14.688f)
                        curveTo(16.063f, 13.813f, 16.5f, 12.75f, 16.5f, 11.5f)
                        curveTo(16.5f, 10.25f, 16.063f, 9.188f, 15.188f, 8.313f)
                        curveTo(14.313f, 7.438f, 13.25f, 7f, 12f, 7f)
                        curveTo(10.75f, 7f, 9.688f, 7.438f, 8.813f, 8.313f)
                        curveTo(7.938f, 9.188f, 7.5f, 10.25f, 7.5f, 11.5f)
                        curveTo(7.5f, 12.75f, 7.938f, 13.813f, 8.813f, 14.688f)
                        curveTo(9.688f, 15.563f, 10.75f, 16f, 12f, 16f)
                        close()
                        moveTo(12f, 14.2f)
                        curveTo(11.25f, 14.2f, 10.613f, 13.938f, 10.087f, 13.413f)
                        curveTo(9.563f, 12.887f, 9.3f, 12.25f, 9.3f, 11.5f)
                        curveTo(9.3f, 10.75f, 9.563f, 10.113f, 10.087f, 9.587f)
                        curveTo(10.613f, 9.063f, 11.25f, 8.8f, 12f, 8.8f)
                        curveTo(12.75f, 8.8f, 13.387f, 9.063f, 13.913f, 9.587f)
                        curveTo(14.438f, 10.113f, 14.7f, 10.75f, 14.7f, 11.5f)
                        curveTo(14.7f, 12.25f, 14.438f, 12.887f, 13.913f, 13.413f)
                        curveTo(13.387f, 13.938f, 12.75f, 14.2f, 12f, 14.2f)
                        close()
                        moveTo(12f, 19f)
                        curveTo(9.567f, 19f, 7.35f, 18.321f, 5.35f, 16.962f)
                        curveTo(3.35f, 15.604f, 1.9f, 13.783f, 1f, 11.5f)
                        curveTo(1.9f, 9.217f, 3.35f, 7.396f, 5.35f, 6.037f)
                        curveTo(7.35f, 4.679f, 9.567f, 4f, 12f, 4f)
                        curveTo(14.433f, 4f, 16.65f, 4.679f, 18.65f, 6.037f)
                        curveTo(20.65f, 7.396f, 22.1f, 9.217f, 23f, 11.5f)
                        curveTo(22.1f, 13.783f, 20.65f, 15.604f, 18.65f, 16.962f)
                        curveTo(16.65f, 18.321f, 14.433f, 19f, 12f, 19f)
                        close()
                    }
                }.build()

        return _Visibility!!
    }

@Suppress("ObjectPropertyName")
private var _Visibility: ImageVector? = null
