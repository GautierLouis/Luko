package xyz.luko.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.PlayArrow: ImageVector
    get() {
        if (_PlayArrow != null) {
            return _PlayArrow!!
        }
        _PlayArrow =
            ImageVector
                .Builder(
                    name = "PlayArrow",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(8f, 17.175f)
                        verticalLineTo(6.825f)
                        curveTo(8f, 6.542f, 8.1f, 6.304f, 8.3f, 6.113f)
                        curveTo(8.5f, 5.921f, 8.733f, 5.825f, 9f, 5.825f)
                        curveTo(9.083f, 5.825f, 9.171f, 5.838f, 9.262f, 5.863f)
                        curveTo(9.354f, 5.888f, 9.442f, 5.925f, 9.525f, 5.975f)
                        lineTo(17.675f, 11.15f)
                        curveTo(17.825f, 11.25f, 17.938f, 11.375f, 18.013f, 11.525f)
                        curveTo(18.087f, 11.675f, 18.125f, 11.833f, 18.125f, 12f)
                        curveTo(18.125f, 12.167f, 18.087f, 12.325f, 18.013f, 12.475f)
                        curveTo(17.938f, 12.625f, 17.825f, 12.75f, 17.675f, 12.85f)
                        lineTo(9.525f, 18.025f)
                        curveTo(9.442f, 18.075f, 9.354f, 18.113f, 9.262f, 18.138f)
                        curveTo(9.171f, 18.163f, 9.083f, 18.175f, 9f, 18.175f)
                        curveTo(8.733f, 18.175f, 8.5f, 18.079f, 8.3f, 17.888f)
                        curveTo(8.1f, 17.696f, 8f, 17.458f, 8f, 17.175f)
                        close()
                    }
                }.build()

        return _PlayArrow!!
    }

@Suppress("ObjectPropertyName")
private var _PlayArrow: ImageVector? = null
