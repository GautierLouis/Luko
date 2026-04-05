package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.RoundedBolt: ImageVector
    get() {
        if (_RoundedBolt != null) {
            return _RoundedBolt!!
        }
        _RoundedBolt = ImageVector.Builder(
            name = "RoundedBolt",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(422f, 728f)
                lineTo(629f, 480f)
                lineTo(469f, 480f)
                lineTo(498f, 253f)
                lineTo(313f, 520f)
                lineTo(452f, 520f)
                lineTo(422f, 728f)
                close()
                moveTo(360f, 600f)
                lineTo(236f, 600f)
                quadTo(212f, 600f, 200.5f, 578.5f)
                quadTo(189f, 557f, 203f, 537f)
                lineTo(502f, 107f)
                quadTo(512f, 93f, 528f, 87.5f)
                quadTo(544f, 82f, 561f, 88f)
                quadTo(578f, 94f, 586f, 109f)
                quadTo(594f, 124f, 592f, 141f)
                lineTo(560f, 400f)
                lineTo(715f, 400f)
                quadTo(741f, 400f, 751.5f, 423f)
                quadTo(762f, 446f, 745f, 466f)
                lineTo(416f, 860f)
                quadTo(405f, 873f, 389f, 877f)
                quadTo(373f, 881f, 358f, 874f)
                quadTo(343f, 867f, 334.5f, 852.5f)
                quadTo(326f, 838f, 328f, 821f)
                lineTo(360f, 600f)
                close()
                moveTo(471f, 490f)
                lineTo(471f, 490f)
                lineTo(471f, 490f)
                lineTo(471f, 490f)
                lineTo(471f, 490f)
                lineTo(471f, 490f)
                close()
            }
        }.build()

        return _RoundedBolt!!
    }

@Suppress("ObjectPropertyName")
private var _RoundedBolt: ImageVector? = null
