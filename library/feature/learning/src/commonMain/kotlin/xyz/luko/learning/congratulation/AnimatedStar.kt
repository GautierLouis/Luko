package xyz.luko.learning.congratulation

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.RoundedStar
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme

@Composable
internal fun AnimatedStar(
    modifier: Modifier = Modifier,
    initialOffset: Offset = Offset.Zero,
    delay: Int = 0,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bounce")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(600 + delay, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounceY"
    )

    Image(
        imageVector = AppIcon.RoundedStar,
        contentDescription = null,
        alignment = Alignment.Center,
        colorFilter = ColorFilter.tint(Color(0xFFFACC15)),
        modifier = modifier
            .offset(
                x = initialOffset.x.dp,
                y = initialOffset.y.dp + offsetY.dp
            )
    )
}

@Preview
@Composable
private fun PreviewAnimatedStar(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        AnimatedStar()
    }
}