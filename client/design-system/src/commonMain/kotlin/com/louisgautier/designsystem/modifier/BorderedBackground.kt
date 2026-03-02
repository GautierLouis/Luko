package com.louisgautier.designsystem.modifier

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color

fun Modifier.borderedBackground(
    border: BorderStroke,
    color: Color,
    shape: RoundedCornerShape
) = this.background(color, shape).border(border, shape).clip(shape)
