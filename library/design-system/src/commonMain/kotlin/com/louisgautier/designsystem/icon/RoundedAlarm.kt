package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.RoundedAlarm: ImageVector
    get() {
        if (_RoundedAlarm != null) {
            return _RoundedAlarm!!
        }
        _RoundedAlarm = ImageVector.Builder(
            name = "RoundedAlarm",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(520f, 504f)
                lineTo(520f, 360f)
                quadTo(520f, 343f, 508.5f, 331.5f)
                quadTo(497f, 320f, 480f, 320f)
                quadTo(463f, 320f, 451.5f, 331.5f)
                quadTo(440f, 343f, 440f, 360f)
                lineTo(440f, 519f)
                quadTo(440f, 527f, 443f, 534.5f)
                quadTo(446f, 542f, 452f, 548f)
                lineTo(564f, 660f)
                quadTo(575f, 671f, 592f, 671f)
                quadTo(609f, 671f, 620f, 660f)
                quadTo(631f, 649f, 631f, 632f)
                quadTo(631f, 615f, 620f, 604f)
                lineTo(520f, 504f)
                close()
                moveTo(480f, 880f)
                quadTo(405f, 880f, 339.5f, 851.5f)
                quadTo(274f, 823f, 225.5f, 774.5f)
                quadTo(177f, 726f, 148.5f, 660.5f)
                quadTo(120f, 595f, 120f, 520f)
                quadTo(120f, 445f, 148.5f, 379.5f)
                quadTo(177f, 314f, 225.5f, 265.5f)
                quadTo(274f, 217f, 339.5f, 188.5f)
                quadTo(405f, 160f, 480f, 160f)
                quadTo(555f, 160f, 620.5f, 188.5f)
                quadTo(686f, 217f, 734.5f, 265.5f)
                quadTo(783f, 314f, 811.5f, 379.5f)
                quadTo(840f, 445f, 840f, 520f)
                quadTo(840f, 595f, 811.5f, 660.5f)
                quadTo(783f, 726f, 734.5f, 774.5f)
                quadTo(686f, 823f, 620.5f, 851.5f)
                quadTo(555f, 880f, 480f, 880f)
                close()
                moveTo(480f, 520f)
                quadTo(480f, 520f, 480f, 520f)
                quadTo(480f, 520f, 480f, 520f)
                quadTo(480f, 520f, 480f, 520f)
                quadTo(480f, 520f, 480f, 520f)
                quadTo(480f, 520f, 480f, 520f)
                quadTo(480f, 520f, 480f, 520f)
                quadTo(480f, 520f, 480f, 520f)
                quadTo(480f, 520f, 480f, 520f)
                close()
                moveTo(82f, 292f)
                quadTo(71f, 281f, 71f, 264f)
                quadTo(71f, 247f, 82f, 236f)
                lineTo(196f, 122f)
                quadTo(207f, 111f, 224f, 111f)
                quadTo(241f, 111f, 252f, 122f)
                quadTo(263f, 133f, 263f, 150f)
                quadTo(263f, 167f, 252f, 178f)
                lineTo(138f, 292f)
                quadTo(127f, 303f, 110f, 303f)
                quadTo(93f, 303f, 82f, 292f)
                close()
                moveTo(878f, 292f)
                quadTo(867f, 303f, 850f, 303f)
                quadTo(833f, 303f, 822f, 292f)
                lineTo(708f, 178f)
                quadTo(697f, 167f, 697f, 150f)
                quadTo(697f, 133f, 708f, 122f)
                quadTo(719f, 111f, 736f, 111f)
                quadTo(753f, 111f, 764f, 122f)
                lineTo(878f, 236f)
                quadTo(889f, 247f, 889f, 264f)
                quadTo(889f, 281f, 878f, 292f)
                close()
                moveTo(480f, 800f)
                quadTo(597f, 800f, 678.5f, 718.5f)
                quadTo(760f, 637f, 760f, 520f)
                quadTo(760f, 403f, 678.5f, 321.5f)
                quadTo(597f, 240f, 480f, 240f)
                quadTo(363f, 240f, 281.5f, 321.5f)
                quadTo(200f, 403f, 200f, 520f)
                quadTo(200f, 637f, 281.5f, 718.5f)
                quadTo(363f, 800f, 480f, 800f)
                close()
            }
        }.build()

        return _RoundedAlarm!!
    }

@Suppress("ObjectPropertyName")
private var _RoundedAlarm: ImageVector? = null
