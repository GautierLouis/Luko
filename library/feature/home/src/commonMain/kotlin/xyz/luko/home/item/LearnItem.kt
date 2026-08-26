package xyz.luko.home.item

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.SessionSettings
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme

@Composable
internal fun LearnItem(
    enabled: Boolean,
    cards: List<SessionSettings>
) {
    val listState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp),
    ) {
        HomeSectionItemTitle(title = "Learn")

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LazyRow(
                state = listState,
                flingBehavior = flingBehavior,
                modifier = Modifier.fillMaxWidth().height(120.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    SessionDrawingCard(
                        modifier = Modifier
                            .fillParentMaxWidth(.65f)
                            .fillMaxHeight()
                            .shimmer(
                                enabled = !enabled,
                                containerColor = Theme.materialColors.primaryContainer.darken(.15f),
                                cornerRadius = 8.dp,
                            )
                    )
                }

                items(
                    count = cards.size,
                ) { index ->
                    ContinueSessionCard(
                        cards[index],
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                    )
                }
            }
        }
    }
}

private fun Modifier.shimmer(
    enabled: Boolean,
    containerColor: Color,
    shimmerColor: Color = Color.White,
    cornerRadius: Dp = 8.dp,
): Modifier = composed {
    if (!enabled) return@composed this

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer-translate"
    )

    val shimmerColors = listOf(
        shimmerColor.copy(alpha = 0f),
        shimmerColor.copy(alpha = 0.3f),
        shimmerColor.copy(alpha = 0f),
    )

    val density = LocalDensity.current

    this.drawWithContent {
        drawRoundRect(
            color = containerColor,
            size = size,
            cornerRadius = CornerRadius(with(density) { cornerRadius.toPx() })
        )
        drawRect(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(translateAnim - 200f, 0f),
                end = Offset(translateAnim, size.height)
            )
        )
    }
}

private fun Color.darken(factor: Float): Color {
    return Color(
        red = red * (1 - factor),
        green = green * (1 - factor),
        blue = blue * (1 - factor),
        alpha = alpha
    )
}

@Preview
@Composable
private fun PreviewLearnItem(
    @PreviewParameter(provider = ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        LearnItem(
            enabled = true,
            cards = listOf(
                SessionSettings(
                    difficultyLevel = DifficultyLevel.HARD,
                    count = 5,
                    frequencyLevel = listOf(CharacterFrequencyLevel.COMMON)
                )
            )
        )
    }
}
