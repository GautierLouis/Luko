package xyz.luko.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.RoundedTarget: ImageVector
    get() {
        if (_RoundedTarget != null) {
            return _RoundedTarget!!
        }
        _RoundedTarget =
            ImageVector
                .Builder(
                    name = "RoundedTarget",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(480f, 600f)
                        quadTo(430f, 600f, 395f, 565f)
                        quadTo(360f, 530f, 360f, 480f)
                        quadTo(360f, 430f, 395f, 395f)
                        quadTo(430f, 360f, 480f, 360f)
                        quadTo(530f, 360f, 565f, 395f)
                        quadTo(600f, 430f, 600f, 480f)
                        quadTo(600f, 530f, 565f, 565f)
                        quadTo(530f, 600f, 480f, 600f)
                        close()
                        moveTo(480f, 880f)
                        quadTo(397f, 880f, 324f, 848.5f)
                        quadTo(251f, 817f, 197f, 763f)
                        quadTo(143f, 709f, 111.5f, 636f)
                        quadTo(80f, 563f, 80f, 480f)
                        quadTo(80f, 397f, 111.5f, 324f)
                        quadTo(143f, 251f, 197f, 197f)
                        quadTo(251f, 143f, 324f, 111.5f)
                        quadTo(397f, 80f, 480f, 80f)
                        quadTo(563f, 80f, 636f, 111.5f)
                        quadTo(709f, 143f, 763f, 197f)
                        quadTo(817f, 251f, 848.5f, 324f)
                        quadTo(880f, 397f, 880f, 480f)
                        quadTo(880f, 563f, 848.5f, 636f)
                        quadTo(817f, 709f, 763f, 763f)
                        quadTo(709f, 817f, 636f, 848.5f)
                        quadTo(563f, 880f, 480f, 880f)
                        close()
                        moveTo(480f, 800f)
                        quadTo(613f, 800f, 706.5f, 706.5f)
                        quadTo(800f, 613f, 800f, 480f)
                        quadTo(800f, 347f, 706.5f, 253.5f)
                        quadTo(613f, 160f, 480f, 160f)
                        quadTo(347f, 160f, 253.5f, 253.5f)
                        quadTo(160f, 347f, 160f, 480f)
                        quadTo(160f, 613f, 253.5f, 706.5f)
                        quadTo(347f, 800f, 480f, 800f)
                        close()
                        moveTo(480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        quadTo(480f, 480f, 480f, 480f)
                        close()
                        moveTo(480f, 680f)
                        quadTo(563f, 680f, 621.5f, 621.5f)
                        quadTo(680f, 563f, 680f, 480f)
                        quadTo(680f, 397f, 621.5f, 338.5f)
                        quadTo(563f, 280f, 480f, 280f)
                        quadTo(397f, 280f, 338.5f, 338.5f)
                        quadTo(280f, 397f, 280f, 480f)
                        quadTo(280f, 563f, 338.5f, 621.5f)
                        quadTo(397f, 680f, 480f, 680f)
                        close()
                    }
                }.build()

        return _RoundedTarget!!
    }

@Suppress("ObjectPropertyName")
private var _RoundedTarget: ImageVector? = null
