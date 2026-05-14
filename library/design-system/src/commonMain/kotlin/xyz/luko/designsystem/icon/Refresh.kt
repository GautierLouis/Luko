package xyz.luko.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.Refresh: ImageVector
    get() {
        if (_Refresh != null) {
            return _Refresh!!
        }
        _Refresh =
            ImageVector
                .Builder(
                    name = "Refresh",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f,
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(12f, 20f)
                        curveTo(9.767f, 20f, 7.875f, 19.225f, 6.325f, 17.675f)
                        curveTo(4.775f, 16.125f, 4f, 14.233f, 4f, 12f)
                        curveTo(4f, 9.767f, 4.775f, 7.875f, 6.325f, 6.325f)
                        curveTo(7.875f, 4.775f, 9.767f, 4f, 12f, 4f)
                        curveTo(13.15f, 4f, 14.25f, 4.238f, 15.3f, 4.713f)
                        curveTo(16.35f, 5.188f, 17.25f, 5.867f, 18f, 6.75f)
                        verticalLineTo(5f)
                        curveTo(18f, 4.717f, 18.096f, 4.479f, 18.288f, 4.287f)
                        curveTo(18.479f, 4.096f, 18.717f, 4f, 19f, 4f)
                        curveTo(19.283f, 4f, 19.521f, 4.096f, 19.712f, 4.287f)
                        curveTo(19.904f, 4.479f, 20f, 4.717f, 20f, 5f)
                        verticalLineTo(10f)
                        curveTo(20f, 10.283f, 19.904f, 10.521f, 19.712f, 10.712f)
                        curveTo(19.521f, 10.904f, 19.283f, 11f, 19f, 11f)
                        horizontalLineTo(14f)
                        curveTo(13.717f, 11f, 13.479f, 10.904f, 13.288f, 10.712f)
                        curveTo(13.096f, 10.521f, 13f, 10.283f, 13f, 10f)
                        curveTo(13f, 9.717f, 13.096f, 9.479f, 13.288f, 9.288f)
                        curveTo(13.479f, 9.096f, 13.717f, 9f, 14f, 9f)
                        horizontalLineTo(17.2f)
                        curveTo(16.667f, 8.067f, 15.938f, 7.333f, 15.012f, 6.8f)
                        curveTo(14.087f, 6.267f, 13.083f, 6f, 12f, 6f)
                        curveTo(10.333f, 6f, 8.917f, 6.583f, 7.75f, 7.75f)
                        curveTo(6.583f, 8.917f, 6f, 10.333f, 6f, 12f)
                        curveTo(6f, 13.667f, 6.583f, 15.083f, 7.75f, 16.25f)
                        curveTo(8.917f, 17.417f, 10.333f, 18f, 12f, 18f)
                        curveTo(13.133f, 18f, 14.171f, 17.712f, 15.113f, 17.138f)
                        curveTo(16.054f, 16.563f, 16.783f, 15.792f, 17.3f, 14.825f)
                        curveTo(17.433f, 14.592f, 17.621f, 14.429f, 17.862f, 14.337f)
                        curveTo(18.104f, 14.246f, 18.35f, 14.242f, 18.6f, 14.325f)
                        curveTo(18.867f, 14.408f, 19.058f, 14.583f, 19.175f, 14.85f)
                        curveTo(19.292f, 15.117f, 19.283f, 15.367f, 19.15f, 15.6f)
                        curveTo(18.467f, 16.933f, 17.492f, 18f, 16.225f, 18.8f)
                        curveTo(14.958f, 19.6f, 13.55f, 20f, 12f, 20f)
                        close()
                    }
                }.build()

        return _Refresh!!
    }

@Suppress("ObjectPropertyName")
private var _Refresh: ImageVector? = null
