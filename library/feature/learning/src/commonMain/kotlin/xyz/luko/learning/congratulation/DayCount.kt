package xyz.luko.learning.congratulation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.luko.designsystem.theme.Theme

@Composable
internal fun DayCount(
    state: StreakRefreshViewModel.UIState,
    newStreak: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            this@Column.AnimatedVisibility(
                visible = !state.showNew,
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(350)
                ) + fadeOut(tween(350))
            ) {
                Text(
                    text = "${newStreak.dec()}",
                    style = Theme.typography.displayLarge,
                    fontWeight = FontWeight.Medium,
                )
            }

            this@Column.AnimatedVisibility(
                visible = state.showNew,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(350, easing = FastOutSlowInEasing)
                ) + fadeIn(tween(350))
            ) {
                Text(
                    text = "$newStreak",
                    style = Theme.typography.displayLarge,
                    fontWeight = FontWeight.Medium,
                    color = Theme.appLevelColors.easy.primary
                )
            }
        }

        Text(
            text = "Day Streak!",
            style = Theme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
    }
}
