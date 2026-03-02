package com.louisgautier.designsystem.token.dimens

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.ai.Gray200

object BorderStrokeDefaults {
    fun zero() = BorderStroke(0.dp, Color.Unspecified)
    fun minimum(color: Color = Gray200) = BorderStroke(1.dp, color)
}