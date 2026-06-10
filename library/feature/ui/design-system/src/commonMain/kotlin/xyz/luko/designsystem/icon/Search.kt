package xyz.luko.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.Search: ImageVector
    get() {
        if (_Search != null) {
            return _Search!!
        }
        _Search =
            ImageVector
                .Builder(
                    name = "Search",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(9.5f, 16f)
                        curveTo(7.683f, 16f, 6.146f, 15.371f, 4.887f, 14.113f)
                        curveTo(3.629f, 12.854f, 3f, 11.317f, 3f, 9.5f)
                        curveTo(3f, 7.683f, 3.629f, 6.146f, 4.887f, 4.887f)
                        curveTo(6.146f, 3.629f, 7.683f, 3f, 9.5f, 3f)
                        curveTo(11.317f, 3f, 12.854f, 3.629f, 14.113f, 4.887f)
                        curveTo(15.371f, 6.146f, 16f, 7.683f, 16f, 9.5f)
                        curveTo(16f, 10.233f, 15.883f, 10.925f, 15.65f, 11.575f)
                        curveTo(15.417f, 12.225f, 15.1f, 12.8f, 14.7f, 13.3f)
                        lineTo(20.3f, 18.9f)
                        curveTo(20.483f, 19.083f, 20.575f, 19.317f, 20.575f, 19.6f)
                        curveTo(20.575f, 19.883f, 20.483f, 20.117f, 20.3f, 20.3f)
                        curveTo(20.117f, 20.483f, 19.883f, 20.575f, 19.6f, 20.575f)
                        curveTo(19.317f, 20.575f, 19.083f, 20.483f, 18.9f, 20.3f)
                        lineTo(13.3f, 14.7f)
                        curveTo(12.8f, 15.1f, 12.225f, 15.417f, 11.575f, 15.65f)
                        curveTo(10.925f, 15.883f, 10.233f, 16f, 9.5f, 16f)
                        close()
                        moveTo(9.5f, 14f)
                        curveTo(10.75f, 14f, 11.813f, 13.563f, 12.688f, 12.688f)
                        curveTo(13.563f, 11.813f, 14f, 10.75f, 14f, 9.5f)
                        curveTo(14f, 8.25f, 13.563f, 7.188f, 12.688f, 6.313f)
                        curveTo(11.813f, 5.438f, 10.75f, 5f, 9.5f, 5f)
                        curveTo(8.25f, 5f, 7.188f, 5.438f, 6.313f, 6.313f)
                        curveTo(5.438f, 7.188f, 5f, 8.25f, 5f, 9.5f)
                        curveTo(5f, 10.75f, 5.438f, 11.813f, 6.313f, 12.688f)
                        curveTo(7.188f, 13.563f, 8.25f, 14f, 9.5f, 14f)
                        close()
                    }
                }.build()

        return _Search!!
    }

@Suppress("ObjectPropertyName")
private var _Search: ImageVector? = null
