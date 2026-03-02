package com.louisgautier.learning.congratulation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedTrophy
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AnimatedRewardIcon(
    modifier: Modifier = Modifier
) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFACC15),
            Color(0xFFF97316)
        ),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    Box(
        modifier = modifier
    ) {
        AnimatedStar(
            modifier = Modifier.size(40.dp).align(Alignment.TopEnd),
            delay = 0
        )

        AnimatedStar(
            modifier = Modifier.size(20.dp)
                .align(Alignment.TopStart),
            initialOffset = Offset(y = 10f, x = 5f),
            delay = 5
        )

        AnimatedStar(
            modifier = Modifier.size(25.dp)
                .align(Alignment.BottomStart),
            initialOffset = Offset(y = -15f, x = 5f),
            delay = 10
        )

        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 60.dp
            )
        ) {
            Box(
                modifier = Modifier.background(brush = gradientBrush),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = AppIcon.RoundedTrophy,
                    contentDescription = null,
                    alignment = Alignment.Center,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .size(150.dp)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun AnimatedRewardIconPreview() {
    AnimatedRewardIcon()
}