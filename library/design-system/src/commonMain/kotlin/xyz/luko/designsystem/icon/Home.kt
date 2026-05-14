package xyz.luko.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.Home: ImageVector
    get() {
        if (_Home != null) {
            return _Home!!
        }
        _Home =
            ImageVector
                .Builder(
                    name = "Home",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(6f, 19f)
                        horizontalLineTo(9f)
                        verticalLineTo(14f)
                        curveTo(9f, 13.717f, 9.096f, 13.479f, 9.288f, 13.288f)
                        curveTo(9.479f, 13.096f, 9.717f, 13f, 10f, 13f)
                        horizontalLineTo(14f)
                        curveTo(14.283f, 13f, 14.521f, 13.096f, 14.712f, 13.288f)
                        curveTo(14.904f, 13.479f, 15f, 13.717f, 15f, 14f)
                        verticalLineTo(19f)
                        horizontalLineTo(18f)
                        verticalLineTo(10f)
                        lineTo(12f, 5.5f)
                        lineTo(6f, 10f)
                        verticalLineTo(19f)
                        close()
                        moveTo(4f, 19f)
                        verticalLineTo(10f)
                        curveTo(4f, 9.683f, 4.071f, 9.383f, 4.213f, 9.1f)
                        curveTo(4.354f, 8.817f, 4.55f, 8.583f, 4.8f, 8.4f)
                        lineTo(10.8f, 3.9f)
                        curveTo(11.15f, 3.633f, 11.55f, 3.5f, 12f, 3.5f)
                        curveTo(12.45f, 3.5f, 12.85f, 3.633f, 13.2f, 3.9f)
                        lineTo(19.2f, 8.4f)
                        curveTo(19.45f, 8.583f, 19.646f, 8.817f, 19.788f, 9.1f)
                        curveTo(19.929f, 9.383f, 20f, 9.683f, 20f, 10f)
                        verticalLineTo(19f)
                        curveTo(20f, 19.55f, 19.804f, 20.021f, 19.413f, 20.413f)
                        curveTo(19.021f, 20.804f, 18.55f, 21f, 18f, 21f)
                        horizontalLineTo(14f)
                        curveTo(13.717f, 21f, 13.479f, 20.904f, 13.288f, 20.712f)
                        curveTo(13.096f, 20.521f, 13f, 20.283f, 13f, 20f)
                        verticalLineTo(15f)
                        horizontalLineTo(11f)
                        verticalLineTo(20f)
                        curveTo(11f, 20.283f, 10.904f, 20.521f, 10.712f, 20.712f)
                        curveTo(10.521f, 20.904f, 10.283f, 21f, 10f, 21f)
                        horizontalLineTo(6f)
                        curveTo(5.45f, 21f, 4.979f, 20.804f, 4.588f, 20.413f)
                        curveTo(4.196f, 20.021f, 4f, 19.55f, 4f, 19f)
                        close()
                    }
                }.build()

        return _Home!!
    }

@Suppress("ObjectPropertyName")
private var _Home: ImageVector? = null
