package xyz.luko.learning.congratulation.streak.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.RoundedBolt
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun StreakDayItem(
    title: String,
    day: StreakDay,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Spacing.small,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Padding.medium, bottom = Padding.large)
    ) {
        Text(
            text = title.first().toString(),
            style = Theme.typography.bodyMedium,
            color = Theme.materialColors.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(
                minFontSize = Theme.typography.bodyLarge.fontSize,
                maxFontSize = Theme.typography.titleLarge.fontSize
            )
        )

        StreakDayContent(
            day = day,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun StreakDayContent(
    day: StreakDay,
    modifier: Modifier = Modifier,
) {

    val streakColor = when {
        day.currentStreak -> Theme.materialColors.primary
        else -> Theme.materialColors.error
    }

    val borderColor = when (day.segment) {
        SegmentPosition.None -> Theme.materialColors.outline
        else -> streakColor
    }

    var animate by remember(day) {
        mutableStateOf(!day.shouldAnimate)
    }

    LaunchedEffect(day.shouldAnimate) {
        delay(600.milliseconds)
        if (day.shouldAnimate) {
            animate = true
        }
    }

    val progress by animateFloatAsState(
        targetValue = when {
            day.segment == SegmentPosition.None -> 0f
            animate -> 1f
            else -> 0f
        },
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "streak-progress"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(36.dp)
                .stroke(!day.currentStreak, streakColor)
                .background(
                    color = streakColor.copy(alpha = .4f),
                    shape = day.shape(progress)
                )
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(28.dp)
                    .background(color = Color.Transparent)
                    .border(
                        border = BorderStrokeDefaults.minimum(borderColor),
                        shape = CircleShape
                    )
            ) {
                this@Column.AnimatedVisibility(
                    visible = progress == 1f,
                    enter = fadeIn(
                        animationSpec = spring(stiffness = Spring.StiffnessMedium)
                    ) + scaleIn(
                        initialScale = 2.5f,
                        transformOrigin = TransformOrigin.Center,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ),
                ) {
                    Icon(
                        imageVector = AppIcon.RoundedBolt,
                        contentDescription = null,
                        tint = streakColor,
                        modifier = Modifier.fillMaxSize().padding(Padding.small)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewStreakDayItem(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {

    val item = previewStreakDays.first()
        .copy(segment = SegmentPosition.Last)

    AppTheme(themeMode) {
        Column {
            StreakDayItem(
                title = "M",
                day = item,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}
