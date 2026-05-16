package xyz.luko.learning.congratulation

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
import kotlinx.coroutines.delay
import xyz.luko.baseui.AdaptiveLayout
import xyz.luko.baseui.AdaptiveLayoutOrientation
import xyz.luko.baseui.AdaptiveLayoutOrientation.COLUMN
import xyz.luko.baseui.AdaptiveLayoutOrientation.ROW
import xyz.luko.baseui.TestTags
import xyz.luko.designsystem.components.metrics.attrs.MetricItem
import xyz.luko.designsystem.components.metrics.attrs.SessionStatistic
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults

@Composable
internal fun RewardCard(
    score: Int,
    questionCount: String,
    time: String,
    parentOrientation: AdaptiveLayoutOrientation,
    modifier: Modifier = Modifier,
) {
    val orientation = if (parentOrientation == ROW) COLUMN else ROW

    val animatedProgress = remember { Animatable(0f) }
    val animatedScore = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(300)
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000),
        )
    }
    LaunchedEffect(score) {
        animatedScore.animateTo(
            targetValue = score.toFloat(),
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
        colors =
            CardDefaults.cardColors(
                containerColor = Theme.materialColors.surfaceContainer,
            ),
        modifier =
            modifier
                .wrapContentHeight()
                .testTag(TestTags.Misc.CONGRATS_CARD),
    ) {
        AdaptiveLayout(
            modifier =
                Modifier
                    .wrapContentHeight()
                    .padding(Padding.large),
            orientation = orientation,
            spacing = Padding.large,
        ) {
            AccumulatedXp(animatedProgress, animatedScore)

            AdaptiveLayout(
                modifier = Modifier.wrapContentHeight(),
                orientation = parentOrientation,
                spacing = Padding.medium,
            ) {
                CurrentSessionMetric(
                    item =
                        MetricItem.SessionMetric(
                            metric = SessionStatistic.QuestionCount,
                            value = questionCount,
                        ),
                    modifier = Modifier.weight(1f).wrapContentHeight(),
                )
                CurrentSessionMetric(
                    item =
                        MetricItem.SessionMetric(
                            metric = SessionStatistic.Time,
                            value = time,
                        ),
                    modifier = Modifier.weight(1f).wrapContentHeight(),
                )
            }
        }
    }
}

@Composable
private fun AccumulatedXp(
    animatedProgress: Animatable<Float, AnimationVector1D>,
    animatedScore: Animatable<Float, AnimationVector1D>,
) {
    var progressSize by remember { mutableStateOf(IntSize.Zero) }

    val size =
        with(LocalDensity.current) {
            if (progressSize == IntSize.Zero) {
                150.dp
            } else {
                progressSize.width.toDp()
            }
        }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier =
                Modifier
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
            progress = { animatedProgress.value },
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = animatedScore.value.toInt().toString(),
                color = Theme.materialColors.tertiary,
                style = Theme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "XP",
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
                score = 50,
                questionCount = "5",
                time = "10:00",
                parentOrientation = COLUMN,
            )
            RewardCard(
                score = 50,
                questionCount = "5",
                time = "10:00",
                parentOrientation = ROW,
            )
        }
    }
}
