package com.louisgautier.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcon.RoundedArrowCoolDown: ImageVector
    get() {
        if (_RoundedArrowCoolDown != null) {
            return _RoundedArrowCoolDown!!
        }
        _RoundedArrowCoolDown = ImageVector.Builder(
            name = "RoundedArrowCoolDown",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(480f, 876f)
                quadTo(472f, 876f, 465f, 873f)
                quadTo(458f, 870f, 452f, 864f)
                lineTo(228f, 641f)
                quadTo(217f, 630f, 217f, 613f)
                quadTo(217f, 596f, 228f, 584f)
                quadTo(240f, 572f, 256.5f, 572f)
                quadTo(273f, 572f, 285f, 584f)
                lineTo(440f, 740f)
                lineTo(440f, 493f)
                quadTo(440f, 476f, 451.5f, 464.5f)
                quadTo(463f, 453f, 480f, 453f)
                quadTo(497f, 453f, 508.5f, 464.5f)
                quadTo(520f, 476f, 520f, 493f)
                lineTo(520f, 740f)
                lineTo(676f, 584f)
                quadTo(687f, 573f, 703.5f, 573f)
                quadTo(720f, 573f, 732f, 585f)
                quadTo(743f, 596f, 743f, 613f)
                quadTo(743f, 630f, 732f, 641f)
                lineTo(508f, 864f)
                quadTo(502f, 870f, 495f, 873f)
                quadTo(488f, 876f, 480f, 876f)
                close()
                moveTo(480f, 373f)
                quadTo(463f, 373f, 451.5f, 361.5f)
                quadTo(440f, 350f, 440f, 333f)
                lineTo(440f, 293f)
                quadTo(440f, 276f, 451.5f, 264.5f)
                quadTo(463f, 253f, 480f, 253f)
                quadTo(497f, 253f, 508.5f, 264.5f)
                quadTo(520f, 276f, 520f, 293f)
                lineTo(520f, 333f)
                quadTo(520f, 350f, 508.5f, 361.5f)
                quadTo(497f, 373f, 480f, 373f)
                close()
                moveTo(480f, 173f)
                quadTo(463f, 173f, 451.5f, 161.5f)
                quadTo(440f, 150f, 440f, 133f)
                lineTo(440f, 133f)
                quadTo(440f, 116f, 451.5f, 104.5f)
                quadTo(463f, 93f, 480f, 93f)
                quadTo(497f, 93f, 508.5f, 104.5f)
                quadTo(520f, 116f, 520f, 133f)
                lineTo(520f, 133f)
                quadTo(520f, 150f, 508.5f, 161.5f)
                quadTo(497f, 173f, 480f, 173f)
                close()
            }
        }.build()

        return _RoundedArrowCoolDown!!
    }

@Suppress("ObjectPropertyName")
private var _RoundedArrowCoolDown: ImageVector? = null
