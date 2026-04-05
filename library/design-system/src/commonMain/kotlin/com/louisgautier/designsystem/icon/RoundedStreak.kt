package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.RoundedStreak: ImageVector
    get() {
        if (_RoundedStreak != null) {
            return _RoundedStreak!!
        }
        _RoundedStreak = ImageVector.Builder(
            name = "RoundedStreak",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(480f, 880f)
                quadTo(346f, 880f, 253f, 787f)
                quadTo(160f, 694f, 160f, 560f)
                quadTo(160f, 447f, 227f, 343f)
                quadTo(294f, 239f, 411f, 161f)
                quadTo(433f, 146f, 456.5f, 159.5f)
                quadTo(480f, 173f, 480f, 200f)
                lineTo(480f, 252f)
                quadTo(480f, 286f, 503.5f, 309f)
                quadTo(527f, 332f, 561f, 332f)
                quadTo(578f, 332f, 593.5f, 324.5f)
                quadTo(609f, 317f, 621f, 303f)
                quadTo(629f, 293f, 641.5f, 290.5f)
                quadTo(654f, 288f, 665f, 296f)
                quadTo(728f, 341f, 764f, 411f)
                quadTo(800f, 481f, 800f, 560f)
                quadTo(800f, 694f, 707f, 787f)
                quadTo(614f, 880f, 480f, 880f)
                close()
                moveTo(240f, 560f)
                quadTo(240f, 612f, 261f, 658.5f)
                quadTo(282f, 705f, 321f, 740f)
                quadTo(320f, 735f, 320f, 731f)
                quadTo(320f, 727f, 320f, 722f)
                quadTo(320f, 690f, 332f, 662f)
                quadTo(344f, 634f, 367f, 611f)
                lineTo(480f, 500f)
                lineTo(593f, 611f)
                quadTo(616f, 634f, 628f, 662f)
                quadTo(640f, 690f, 640f, 722f)
                quadTo(640f, 727f, 640f, 731f)
                quadTo(640f, 735f, 639f, 740f)
                quadTo(678f, 705f, 699f, 658.5f)
                quadTo(720f, 612f, 720f, 560f)
                quadTo(720f, 510f, 701.5f, 465.5f)
                quadTo(683f, 421f, 648f, 386f)
                quadTo(648f, 386f, 648f, 386f)
                quadTo(648f, 386f, 648f, 386f)
                quadTo(628f, 399f, 606f, 405.5f)
                quadTo(584f, 412f, 561f, 412f)
                quadTo(499f, 412f, 453.5f, 371f)
                quadTo(408f, 330f, 401f, 270f)
                lineTo(401f, 270f)
                quadTo(401f, 270f, 401f, 270f)
                quadTo(401f, 270f, 401f, 270f)
                quadTo(323f, 336f, 281.5f, 410.5f)
                quadTo(240f, 485f, 240f, 560f)
                close()
                moveTo(480f, 612f)
                lineTo(423f, 668f)
                quadTo(412f, 679f, 406f, 693f)
                quadTo(400f, 707f, 400f, 722f)
                quadTo(400f, 754f, 423.5f, 777f)
                quadTo(447f, 800f, 480f, 800f)
                quadTo(513f, 800f, 536.5f, 777f)
                quadTo(560f, 754f, 560f, 722f)
                quadTo(560f, 706f, 554f, 692.5f)
                quadTo(548f, 679f, 537f, 668f)
                lineTo(480f, 612f)
                close()
            }
        }.build()

        return _RoundedStreak!!
    }

@Suppress("ObjectPropertyName")
private var _RoundedStreak: ImageVector? = null
