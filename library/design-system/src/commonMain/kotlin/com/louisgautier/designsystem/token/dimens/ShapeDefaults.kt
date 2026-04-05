package com.louisgautier.designsystem.token.dimens

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object ShapeDefaults {
    fun tag() = RoundedCornerShape(6.dp)
    fun card() = RoundedCornerShape(16.dp)
    fun button() = RoundedCornerShape(8.dp)
    fun roundButton() = RoundedCornerShape(100)
    fun bottomSheet() = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
}