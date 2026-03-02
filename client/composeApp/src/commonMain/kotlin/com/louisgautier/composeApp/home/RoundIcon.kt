package com.louisgautier.composeApp.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedBarChart
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RoundIcon(
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color,
    size: Dp = 24.dp,
    padding: Dp = 8.dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(contentColor),
            modifier = Modifier
                .size(size)
                .background(containerColor, CircleShape)
                .padding(padding)
        )
    }
}

@Preview
@Composable
fun RoundIconPreview() {
    RoundIcon(
        icon = AppIcon.RoundedBarChart,
        containerColor = Color.Yellow,
        contentColor = Color.Black,
        size = 36.dp,
        padding = 8.dp,
        modifier = Modifier
    )
}