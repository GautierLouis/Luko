package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.Check: ImageVector
    get() {
        if (_Check != null) {
            return _Check!!
        }
        _Check =
            ImageVector
                .Builder(
                    name = "Check",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(9.55f, 15.15f)
                        lineTo(18.025f, 6.675f)
                        curveTo(18.225f, 6.475f, 18.458f, 6.375f, 18.725f, 6.375f)
                        curveTo(18.992f, 6.375f, 19.225f, 6.475f, 19.425f, 6.675f)
                        curveTo(19.625f, 6.875f, 19.725f, 7.113f, 19.725f, 7.387f)
                        curveTo(19.725f, 7.662f, 19.625f, 7.9f, 19.425f, 8.1f)
                        lineTo(10.25f, 17.3f)
                        curveTo(10.05f, 17.5f, 9.817f, 17.6f, 9.55f, 17.6f)
                        curveTo(9.283f, 17.6f, 9.05f, 17.5f, 8.85f, 17.3f)
                        lineTo(4.55f, 13f)
                        curveTo(4.35f, 12.8f, 4.254f, 12.563f, 4.262f, 12.288f)
                        curveTo(4.271f, 12.012f, 4.375f, 11.775f, 4.575f, 11.575f)
                        curveTo(4.775f, 11.375f, 5.012f, 11.275f, 5.287f, 11.275f)
                        curveTo(5.563f, 11.275f, 5.8f, 11.375f, 6f, 11.575f)
                        lineTo(9.55f, 15.15f)
                        close()
                    }
                }.build()

        return _Check!!
    }

@Suppress("ObjectPropertyName")
private var _Check: ImageVector? = null
