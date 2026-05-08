package com.louisgautier.learning.congratulation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.louisgautier.baseui.AdaptiveLayout
import com.louisgautier.baseui.AdaptiveLayoutOrientation
import com.louisgautier.baseui.AdaptiveLayoutOrientation.COLUMN
import com.louisgautier.baseui.AdaptiveLayoutOrientation.ROW
import com.louisgautier.baseui.TestTags
import com.louisgautier.designsystem.components.metrics.attrs.MetricItem
import com.louisgautier.designsystem.components.metrics.attrs.SessionStatistic
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.BorderStrokeDefaults
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import kotlinx.coroutines.delay

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
            animationSpec = tween(durationMillis = 1000)
        )
    }
    LaunchedEffect(score) {
        animatedScore.animateTo(
            targetValue = score.toFloat(),
            animationSpec = tween(
                durationMillis = 500,
                easing = EaseOutCubic
            )
        )
    }

    Card(
        shape = ShapeDefaults.card(),
        border = BorderStrokeDefaults.minimum(Theme.materialColors.outline),
        colors = CardDefaults.cardColors(
            containerColor = Theme.materialColors.surfaceContainer
        ),
        modifier = modifier.wrapContentHeight()
            .testTag(TestTags.Misc.CONGRATS_CARD)
    ) {
        AdaptiveLayout(
            modifier = Modifier
                .wrapContentHeight()
                .padding(Padding.large),
            orientation = orientation,
            spacing = Padding.large
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(150.dp),
                    strokeWidth = 15.dp,
                    trackColor = Theme.materialColors.tertiaryContainer,
                    color = Theme.materialColors.tertiary,
                    strokeCap = StrokeCap.Round,
                    progress = { animatedProgress.value }
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = animatedScore.value.toInt().toString(),
                        color = Theme.materialColors.tertiary,
                        style = Theme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "XP",
                        style = Theme.typography.bodyLarge,
                        color = Theme.materialColors.tertiary
                    )
                }
            }

            AdaptiveLayout(
                modifier = Modifier.wrapContentHeight(),
                orientation = parentOrientation,
                spacing = Padding.medium
            ) {
                CurrentSessionMetric(
                    item = MetricItem.SessionMetric(
                        metric = SessionStatistic.QuestionCount,
                        value = questionCount
                    ),
                    modifier = Modifier.weight(1f).wrapContentHeight()
                )
                CurrentSessionMetric(
                    item = MetricItem.SessionMetric(
                        metric = SessionStatistic.Time,
                        value = time
                    ),
                    modifier = Modifier.weight(1f).wrapContentHeight()
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRewardCard(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            RewardCard(
                score = 50,
                questionCount = "5",
                time = "10:00",
                parentOrientation = COLUMN
            )
            RewardCard(
                score = 50,
                questionCount = "5",
                time = "10:00",
                parentOrientation = ROW
            )
        }
    }
}