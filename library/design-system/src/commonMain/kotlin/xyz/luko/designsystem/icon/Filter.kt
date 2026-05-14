package xyz.luko.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.Filter: ImageVector
    get() {
        if (_Filter != null) {
            return _Filter!!
        }
        _Filter =
            ImageVector
                .Builder(
                    name = "Filter",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(11f, 20f)
                        curveTo(10.717f, 20f, 10.479f, 19.904f, 10.288f, 19.712f)
                        curveTo(10.096f, 19.521f, 10f, 19.283f, 10f, 19f)
                        verticalLineTo(13f)
                        lineTo(4.2f, 5.6f)
                        curveTo(3.95f, 5.267f, 3.912f, 4.917f, 4.087f, 4.55f)
                        curveTo(4.262f, 4.183f, 4.567f, 4f, 5f, 4f)
                        horizontalLineTo(19f)
                        curveTo(19.433f, 4f, 19.737f, 4.183f, 19.913f, 4.55f)
                        curveTo(20.087f, 4.917f, 20.05f, 5.267f, 19.8f, 5.6f)
                        lineTo(14f, 13f)
                        verticalLineTo(19f)
                        curveTo(14f, 19.283f, 13.904f, 19.521f, 13.712f, 19.712f)
                        curveTo(13.521f, 19.904f, 13.283f, 20f, 13f, 20f)
                        horizontalLineTo(11f)
                        close()
                    }
                }.build()

        return _Filter!!
    }

@Suppress("ObjectPropertyName")
private var _Filter: ImageVector? = null
