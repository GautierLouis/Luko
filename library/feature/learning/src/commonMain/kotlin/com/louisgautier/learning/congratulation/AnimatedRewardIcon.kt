package com.louisgautier.learning.congratulation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedTrophy
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding

@Composable
internal fun AnimatedRewardIcon(
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
                containerColor = Theme.materialColors.background
            ),
            modifier = Modifier
                .wrapContentSize()
                .padding(Padding.large),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 60.dp
            )
        ) {
            Box(
                modifier = Modifier.background(brush = gradientBrush),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = AppIcon.RoundedTrophy,
                    contentDescription = null,
                    tint = Theme.materialColors.background,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(Padding.large)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAnimatedRewardIcon(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        AnimatedRewardIcon()
    }
}