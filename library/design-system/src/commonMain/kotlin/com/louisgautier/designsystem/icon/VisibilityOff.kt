package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.VisibilityOff: ImageVector
    get() {
        if (_VisibilityOff != null) {
            return _VisibilityOff!!
        }
        _VisibilityOff =
            ImageVector
                .Builder(
                    name = "VisibilityOff",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(19.8f, 22.6f)
                        lineTo(15.6f, 18.45f)
                        curveTo(15.017f, 18.633f, 14.429f, 18.771f, 13.837f, 18.863f)
                        curveTo(13.246f, 18.954f, 12.633f, 19f, 12f, 19f)
                        curveTo(9.483f, 19f, 7.242f, 18.304f, 5.275f, 16.913f)
                        curveTo(3.308f, 15.521f, 1.883f, 13.717f, 1f, 11.5f)
                        curveTo(1.35f, 10.617f, 1.792f, 9.796f, 2.325f, 9.038f)
                        curveTo(2.858f, 8.279f, 3.467f, 7.6f, 4.15f, 7f)
                        lineTo(1.4f, 4.2f)
                        lineTo(2.8f, 2.8f)
                        lineTo(21.2f, 21.2f)
                        lineTo(19.8f, 22.6f)
                        close()
                        moveTo(12f, 16f)
                        curveTo(12.183f, 16f, 12.354f, 15.992f, 12.512f, 15.975f)
                        curveTo(12.671f, 15.958f, 12.842f, 15.925f, 13.025f, 15.875f)
                        lineTo(7.625f, 10.475f)
                        curveTo(7.575f, 10.658f, 7.542f, 10.829f, 7.525f, 10.988f)
                        curveTo(7.508f, 11.146f, 7.5f, 11.317f, 7.5f, 11.5f)
                        curveTo(7.5f, 12.75f, 7.938f, 13.813f, 8.813f, 14.688f)
                        curveTo(9.688f, 15.563f, 10.75f, 16f, 12f, 16f)
                        close()
                        moveTo(19.3f, 16.45f)
                        lineTo(16.125f, 13.3f)
                        curveTo(16.242f, 13.017f, 16.333f, 12.729f, 16.4f, 12.438f)
                        curveTo(16.467f, 12.146f, 16.5f, 11.833f, 16.5f, 11.5f)
                        curveTo(16.5f, 10.25f, 16.063f, 9.188f, 15.188f, 8.313f)
                        curveTo(14.313f, 7.438f, 13.25f, 7f, 12f, 7f)
                        curveTo(11.667f, 7f, 11.354f, 7.033f, 11.063f, 7.1f)
                        curveTo(10.771f, 7.167f, 10.483f, 7.267f, 10.2f, 7.4f)
                        lineTo(7.65f, 4.85f)
                        curveTo(8.333f, 4.567f, 9.033f, 4.354f, 9.75f, 4.213f)
                        curveTo(10.467f, 4.071f, 11.217f, 4f, 12f, 4f)
                        curveTo(14.517f, 4f, 16.758f, 4.696f, 18.725f, 6.088f)
                        curveTo(20.692f, 7.479f, 22.117f, 9.283f, 23f, 11.5f)
                        curveTo(22.617f, 12.483f, 22.112f, 13.396f, 21.487f, 14.238f)
                        curveTo(20.862f, 15.079f, 20.133f, 15.817f, 19.3f, 16.45f)
                        close()
                        moveTo(14.675f, 11.85f)
                        lineTo(11.675f, 8.85f)
                        curveTo(12.142f, 8.767f, 12.571f, 8.804f, 12.962f, 8.963f)
                        curveTo(13.354f, 9.121f, 13.692f, 9.35f, 13.975f, 9.65f)
                        curveTo(14.258f, 9.95f, 14.462f, 10.296f, 14.587f, 10.688f)
                        curveTo(14.712f, 11.079f, 14.742f, 11.467f, 14.675f, 11.85f)
                        close()
                    }
                }.build()

        return _VisibilityOff!!
    }

@Suppress("ObjectPropertyName")
private var _VisibilityOff: ImageVector? = null
