package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.Reset: ImageVector
    get() {
        if (_Reset != null) {
            return _Reset!!
        }
        _Reset =
            ImageVector
                .Builder(
                    name = "Reset",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(480f, 880f)
                        quadTo(405f, 880f, 339.5f, 851.5f)
                        quadTo(274f, 823f, 225.5f, 774.5f)
                        quadTo(177f, 726f, 148.5f, 660.5f)
                        quadTo(120f, 595f, 120f, 520f)
                        quadTo(120f, 445f, 148.5f, 379.5f)
                        quadTo(177f, 314f, 225.5f, 265.5f)
                        quadTo(274f, 217f, 339.5f, 188.5f)
                        quadTo(405f, 160f, 480f, 160f)
                        lineTo(486f, 160f)
                        lineTo(452f, 126f)
                        quadTo(441f, 115f, 441f, 98.5f)
                        quadTo(441f, 82f, 452f, 70f)
                        quadTo(464f, 58f, 480.5f, 57.5f)
                        quadTo(497f, 57f, 509f, 69f)
                        lineTo(612f, 172f)
                        quadTo(623f, 183f, 623f, 200f)
                        quadTo(623f, 217f, 612f, 228f)
                        lineTo(509f, 331f)
                        quadTo(497f, 343f, 480.5f, 342.5f)
                        quadTo(464f, 342f, 452f, 330f)
                        quadTo(441f, 318f, 441f, 301.5f)
                        quadTo(441f, 285f, 452f, 274f)
                        lineTo(486f, 240f)
                        lineTo(480f, 240f)
                        quadTo(363f, 240f, 281.5f, 321.5f)
                        quadTo(200f, 403f, 200f, 520f)
                        quadTo(200f, 637f, 281.5f, 718.5f)
                        quadTo(363f, 800f, 480f, 800f)
                        quadTo(586f, 800f, 665f, 731f)
                        quadTo(744f, 662f, 758f, 557f)
                        quadTo(760f, 541f, 772f, 530.5f)
                        quadTo(784f, 520f, 800f, 520f)
                        quadTo(816f, 520f, 828f, 530f)
                        quadTo(840f, 540f, 838f, 555f)
                        quadTo(824f, 694f, 722f, 787f)
                        quadTo(620f, 880f, 480f, 880f)
                        close()
                    }
                }.build()

        return _Reset!!
    }

@Suppress("ObjectPropertyName")
private var _Reset: ImageVector? = null
