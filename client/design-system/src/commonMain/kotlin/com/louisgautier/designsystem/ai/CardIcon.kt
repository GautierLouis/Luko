package com.louisgautier.designsystem.ai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun CardIcon(
    backgroundColor: Color,
    icon: @Composable () -> Unit,
) {
    Box(
        Modifier
            .padding(top = 2.dp)
            .size(36.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}