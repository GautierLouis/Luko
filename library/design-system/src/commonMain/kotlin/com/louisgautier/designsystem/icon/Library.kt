package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.Library: ImageVector
    get() {
        if (_Library != null) {
            return _Library!!
        }
        _Library = ImageVector.Builder(
            name = "Library",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 22.5f)
                curveTo(10.8f, 21.367f, 9.425f, 20.5f, 7.875f, 19.9f)
                curveTo(6.325f, 19.3f, 4.7f, 19f, 3f, 19f)
                verticalLineTo(8f)
                curveTo(4.683f, 8f, 6.3f, 8.304f, 7.85f, 8.913f)
                curveTo(9.4f, 9.521f, 10.783f, 10.4f, 12f, 11.55f)
                curveTo(13.217f, 10.4f, 14.6f, 9.521f, 16.15f, 8.913f)
                curveTo(17.7f, 8.304f, 19.317f, 8f, 21f, 8f)
                verticalLineTo(19f)
                curveTo(19.283f, 19f, 17.654f, 19.3f, 16.112f, 19.9f)
                curveTo(14.571f, 20.5f, 13.2f, 21.367f, 12f, 22.5f)
                close()
                moveTo(12f, 19.9f)
                curveTo(13.05f, 19.117f, 14.167f, 18.492f, 15.35f, 18.025f)
                curveTo(16.533f, 17.558f, 17.75f, 17.25f, 19f, 17.1f)
                verticalLineTo(10.2f)
                curveTo(17.783f, 10.417f, 16.587f, 10.854f, 15.413f, 11.512f)
                curveTo(14.238f, 12.171f, 13.1f, 13.05f, 12f, 14.15f)
                curveTo(10.9f, 13.05f, 9.762f, 12.171f, 8.587f, 11.512f)
                curveTo(7.412f, 10.854f, 6.217f, 10.417f, 5f, 10.2f)
                verticalLineTo(17.1f)
                curveTo(6.25f, 17.25f, 7.467f, 17.558f, 8.65f, 18.025f)
                curveTo(9.833f, 18.492f, 10.95f, 19.117f, 12f, 19.9f)
                close()
                moveTo(12f, 9f)
                curveTo(10.9f, 9f, 9.958f, 8.608f, 9.175f, 7.825f)
                curveTo(8.392f, 7.042f, 8f, 6.1f, 8f, 5f)
                curveTo(8f, 3.9f, 8.392f, 2.958f, 9.175f, 2.175f)
                curveTo(9.958f, 1.392f, 10.9f, 1f, 12f, 1f)
                curveTo(13.1f, 1f, 14.042f, 1.392f, 14.825f, 2.175f)
                curveTo(15.608f, 2.958f, 16f, 3.9f, 16f, 5f)
                curveTo(16f, 6.1f, 15.608f, 7.042f, 14.825f, 7.825f)
                curveTo(14.042f, 8.608f, 13.1f, 9f, 12f, 9f)
                close()
                moveTo(12f, 7f)
                curveTo(12.55f, 7f, 13.021f, 6.804f, 13.413f, 6.412f)
                curveTo(13.804f, 6.021f, 14f, 5.55f, 14f, 5f)
                curveTo(14f, 4.45f, 13.804f, 3.979f, 13.413f, 3.588f)
                curveTo(13.021f, 3.196f, 12.55f, 3f, 12f, 3f)
                curveTo(11.45f, 3f, 10.979f, 3.196f, 10.587f, 3.588f)
                curveTo(10.196f, 3.979f, 10f, 4.45f, 10f, 5f)
                curveTo(10f, 5.55f, 10.196f, 6.021f, 10.587f, 6.412f)
                curveTo(10.979f, 6.804f, 11.45f, 7f, 12f, 7f)
                close()
            }
        }.build()

        return _Library!!
    }

@Suppress("ObjectPropertyName")
private var _Library: ImageVector? = null
