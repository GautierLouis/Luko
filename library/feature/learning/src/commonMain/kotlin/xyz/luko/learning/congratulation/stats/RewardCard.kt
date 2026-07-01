package xyz.luko.learning.congratulation.stats

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import xyz.luko.ui.core.TestTags
import xyz.luko.ui.core.adaptive.AdaptiveContainer
import xyz.luko.ui.designsystem.components.metrics.attrs.MetricItem
import xyz.luko.ui.designsystem.components.metrics.attrs.SessionStatistic
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.ShapeDefaults
import xyz.luko.ui.designsystem.token.dimens.Spacing
import xyz.luko.utils.toPercentage

@Composable
internal fun RewardCard(
    startAnim: Boolean,
    avgAccuracy: Float,
    questionCount: String,
    time: String,
    useRow: Boolean,
    modifier: Modifier = Modifier,
) {
    val animatedProgress = remember { Animatable(0f) }
    val animatedScore = remember { Animatable(0f) }

    val metric1: @Composable (Modifier) -> Unit = {
        CurrentSessionMetric(
            item =
                MetricItem.SessionMetric(
                    metric = SessionStatistic.QuestionCount,
                    value = questionCount,
                ),
            modifier = it.wrapContentHeight(),
        )
    }

    val metric2: @Composable (Modifier) -> Unit = {
        CurrentSessionMetric(
            item =
                MetricItem.SessionMetric(
                    metric = SessionStatistic.Time,
                    value = time,
                ),
            modifier = it.wrapContentHeight(),
        )
    }

    LaunchedEffect(startAnim) {
        if (startAnim) {
            animatedProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000),
            )
        }
    }

    LaunchedEffect(avgAccuracy) {
        animatedScore.animateTo(
            targetValue = avgAccuracy,
            animationSpec =
                tween(
                    durationMillis = 500,
                    easing = EaseOutCubic,
                ),
        )
    }

    Card(
        shape = ShapeDefaults.card(),
        border = BorderStrokeDefaults.minimum(Theme.materialColors.outline),
        colors = CardDefaults.cardColors(
            containerColor = Theme.materialColors.surfaceContainer,
        ),
        modifier = modifier.testTag(TestTags.Misc.CONGRATS_CARD),
    ) {
        AdaptiveContainer(
            modifier = Modifier.padding(Padding.medium),
            useRow = useRow,
            verticalAlignment = Alignment.CenterVertically,
            verticalArrangement = Spacing.medium,
            horizontalArrangement = Spacing.medium,
        ) { parentModifier ->
            OverallAccuracy(
                modifier = parentModifier,
                animatedProgress = animatedProgress,
                animatedScore = animatedScore
            )
            AdaptiveContainer(
                modifier = parentModifier,
                useRow = !useRow,
                horizontalArrangement = Spacing.medium,
                verticalArrangement = Spacing.medium
            ) { itemModifier ->
                metric1(itemModifier.fillMaxWidth())
                metric2(itemModifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun OverallAccuracy(
    animatedProgress: Animatable<Float, AnimationVector1D>,
    animatedScore: Animatable<Float, AnimationVector1D>,
    modifier: Modifier = Modifier
) {
    var progressSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current
    val size by remember {
        derivedStateOf {
            with(density) {
                if (progressSize == IntSize.Zero) {
                    150.dp
                } else {
                    progressSize.width.toDp()
                }
            }
        }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .heightIn(max = 150.dp)
                .widthIn(max = 150.dp)
                .aspectRatio(1f)
                .onGloballyPositioned {
                    progressSize = it.size
                },
            strokeWidth = size * 0.1f,
            trackColor = Theme.materialColors.tertiaryContainer,
            color = Theme.materialColors.tertiary,
            strokeCap = StrokeCap.Round,
            gapSize = (-10).dp,
            progress = { animatedProgress.value },
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = animatedScore.value.toPercentage(),
                color = Theme.materialColors.tertiary,
                style = Theme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "Accuracy",
                style = Theme.typography.bodyLarge,
                color = Theme.materialColors.tertiary,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRewardCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            RewardCard(
                startAnim = false,
                avgAccuracy = 50f,
                questionCount = "5",
                time = "10:00",
                useRow = false,
            )
            RewardCard(
                startAnim = false,
                avgAccuracy = 50f,
                questionCount = "5",
                time = "10:00",
                useRow = true,
            )
        }
    }
}
