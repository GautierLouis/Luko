package com.louisgautier.learning.congratulation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.token.dimens.BorderStrokeDefaults
import com.louisgautier.designsystem.SessionMetric
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.designsystem.ai.Orange100
import com.louisgautier.designsystem.ai.Orange500
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RewardCard(
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
        border = BorderStrokeDefaults.minimum(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(150.dp),
                    strokeWidth = 15.dp,
                    trackColor = Orange100,
                    color = Orange500,
                    strokeCap = StrokeCap.Round,
                    progress = { animatedProgress.value }
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = animatedScore.value.toInt().toString(),
                        fontSize = 24.sp,
                        color = Orange500,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "XP",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CurrentSessionMetric(
                    metric = SessionMetric.QuestionCount,
                    value = questionCount,
                    modifier = Modifier.weight(1f)
                )
                CurrentSessionMetric(
                    metric = SessionMetric.Time,
                    value = time,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
@Preview
fun RewardCardPreview() {
    RewardCard(50, "5", "10:00")
}