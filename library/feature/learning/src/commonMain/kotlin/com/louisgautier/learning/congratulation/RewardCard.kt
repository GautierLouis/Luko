package com.louisgautier.learning.congratulation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.metrics.attrs.MetricItem
import com.louisgautier.designsystem.components.metrics.attrs.SessionStatistic
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.BorderStrokeDefaults
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.designsystem.token.dimens.Spacing
import kotlinx.coroutines.delay

@Composable
internal fun RewardCard(
    score: Int,
    questionCount: String,
    time: String,
    modifier: Modifier = Modifier,
) {

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
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(Padding.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Spacing.large
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(150.dp),
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Spacing.medium
            ) {
                CurrentSessionMetric(
                    item = MetricItem.SessionMetric(
                        metric = SessionStatistic.QuestionCount,
                        value = questionCount
                    ),
                    modifier = Modifier.weight(1f)
                )
                CurrentSessionMetric(
                    item = MetricItem.SessionMetric(
                        metric = SessionStatistic.Time,
                        value = time
                    ),
                    modifier = Modifier.weight(1f)
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
        RewardCard(50, "5", "10:00")
    }
}