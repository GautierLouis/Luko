package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.RoundedCrown: ImageVector
    get() {
        if (_RoundedCrown != null) {
            return _RoundedCrown!!
        }
        _RoundedCrown =
            ImageVector
                .Builder(
                    name = "RoundedCrown",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 960f,
                    viewportHeight = 960f,
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(240f, 800f)
                        quadTo(223f, 800f, 211.5f, 788.5f)
                        quadTo(200f, 777f, 200f, 760f)
                        quadTo(200f, 743f, 211.5f, 731.5f)
                        quadTo(223f, 720f, 240f, 720f)
                        lineTo(720f, 720f)
                        quadTo(737f, 720f, 748.5f, 731.5f)
                        quadTo(760f, 743f, 760f, 760f)
                        quadTo(760f, 777f, 748.5f, 788.5f)
                        quadTo(737f, 800f, 720f, 800f)
                        lineTo(240f, 800f)
                        close()
                        moveTo(268f, 660f)
                        quadTo(239f, 660f, 216.5f, 641f)
                        quadTo(194f, 622f, 189f, 593f)
                        lineTo(149f, 339f)
                        quadTo(147f, 339f, 144.5f, 339.5f)
                        quadTo(142f, 340f, 140f, 340f)
                        quadTo(115f, 340f, 97.5f, 322.5f)
                        quadTo(80f, 305f, 80f, 280f)
                        quadTo(80f, 255f, 97.5f, 237.5f)
                        quadTo(115f, 220f, 140f, 220f)
                        quadTo(165f, 220f, 182.5f, 237.5f)
                        quadTo(200f, 255f, 200f, 280f)
                        quadTo(200f, 287f, 198.5f, 293f)
                        quadTo(197f, 299f, 195f, 304f)
                        lineTo(320f, 360f)
                        quadTo(320f, 360f, 320f, 360f)
                        quadTo(320f, 360f, 320f, 360f)
                        lineTo(445f, 189f)
                        quadTo(434f, 181f, 427f, 168f)
                        quadTo(420f, 155f, 420f, 140f)
                        quadTo(420f, 115f, 437.5f, 97.5f)
                        quadTo(455f, 80f, 480f, 80f)
                        quadTo(505f, 80f, 522.5f, 97.5f)
                        quadTo(540f, 115f, 540f, 140f)
                        quadTo(540f, 155f, 533f, 168f)
                        quadTo(526f, 181f, 515f, 189f)
                        lineTo(640f, 360f)
                        quadTo(640f, 360f, 640f, 360f)
                        quadTo(640f, 360f, 640f, 360f)
                        lineTo(765f, 304f)
                        quadTo(763f, 299f, 761.5f, 293f)
                        quadTo(760f, 287f, 760f, 280f)
                        quadTo(760f, 255f, 777.5f, 237.5f)
                        quadTo(795f, 220f, 820f, 220f)
                        quadTo(845f, 220f, 862.5f, 237.5f)
                        quadTo(880f, 255f, 880f, 280f)
                        quadTo(880f, 305f, 862.5f, 322.5f)
                        quadTo(845f, 340f, 820f, 340f)
                        quadTo(818f, 340f, 815.5f, 339.5f)
                        quadTo(813f, 339f, 811f, 339f)
                        lineTo(771f, 593f)
                        quadTo(766f, 622f, 743.5f, 641f)
                        quadTo(721f, 660f, 692f, 660f)
                        lineTo(268f, 660f)
                        close()
                        moveTo(268f, 580f)
                        lineTo(692f, 580f)
                        quadTo(692f, 580f, 692f, 580f)
                        quadTo(692f, 580f, 692f, 580f)
                        lineTo(718f, 413f)
                        lineTo(672f, 433f)
                        quadTo(646f, 444f, 619f, 437f)
                        quadTo(592f, 430f, 575f, 407f)
                        lineTo(480f, 276f)
                        lineTo(385f, 407f)
                        quadTo(368f, 430f, 341f, 437f)
                        quadTo(314f, 444f, 288f, 433f)
                        lineTo(242f, 413f)
                        lineTo(268f, 580f)
                        quadTo(268f, 580f, 268f, 580f)
                        quadTo(268f, 580f, 268f, 580f)
                        close()
                        moveTo(480f, 580f)
                        quadTo(480f, 580f, 480f, 580f)
                        quadTo(480f, 580f, 480f, 580f)
                        lineTo(480f, 580f)
                        lineTo(480f, 580f)
                        quadTo(480f, 580f, 480f, 580f)
                        quadTo(480f, 580f, 480f, 580f)
                        lineTo(480f, 580f)
                        lineTo(480f, 580f)
                        quadTo(480f, 580f, 480f, 580f)
                        quadTo(480f, 580f, 480f, 580f)
                        lineTo(480f, 580f)
                        lineTo(480f, 580f)
                        quadTo(480f, 580f, 480f, 580f)
                        quadTo(480f, 580f, 480f, 580f)
                        close()
                    }
                }.build()

        return _RoundedCrown!!
    }

@Suppress("ObjectPropertyName")
private var _RoundedCrown: ImageVector? = null
