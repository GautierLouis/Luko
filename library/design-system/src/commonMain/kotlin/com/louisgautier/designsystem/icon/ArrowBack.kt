package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.ArrowBack: ImageVector
    get() {
        if (_ArrowBack != null) {
            return _ArrowBack!!
        }
        _ArrowBack = ImageVector.Builder(
            name = "ArrowBack",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(9.55f, 12f)
                lineTo(16.9f, 19.35f)
                curveTo(17.15f, 19.6f, 17.271f, 19.892f, 17.263f, 20.225f)
                curveTo(17.254f, 20.558f, 17.125f, 20.85f, 16.875f, 21.1f)
                curveTo(16.625f, 21.35f, 16.333f, 21.475f, 16f, 21.475f)
                curveTo(15.667f, 21.475f, 15.375f, 21.35f, 15.125f, 21.1f)
                lineTo(7.425f, 13.425f)
                curveTo(7.225f, 13.225f, 7.075f, 13f, 6.975f, 12.75f)
                curveTo(6.875f, 12.5f, 6.825f, 12.25f, 6.825f, 12f)
                curveTo(6.825f, 11.75f, 6.875f, 11.5f, 6.975f, 11.25f)
                curveTo(7.075f, 11f, 7.225f, 10.775f, 7.425f, 10.575f)
                lineTo(15.125f, 2.875f)
                curveTo(15.375f, 2.625f, 15.671f, 2.504f, 16.013f, 2.513f)
                curveTo(16.354f, 2.521f, 16.65f, 2.65f, 16.9f, 2.9f)
                curveTo(17.15f, 3.15f, 17.275f, 3.442f, 17.275f, 3.775f)
                curveTo(17.275f, 4.108f, 17.15f, 4.4f, 16.9f, 4.65f)
                lineTo(9.55f, 12f)
                close()
            }
        }.build()

        return _ArrowBack!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowBack: ImageVector? = null
