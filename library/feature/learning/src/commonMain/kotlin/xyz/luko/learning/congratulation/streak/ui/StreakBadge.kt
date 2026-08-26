package xyz.luko.learning.congratulation.streak.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.learning.congratulation.streak.dayStreakPreview
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.RoundedBolt
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.ui.designsystem.token.dimens.Padding

@Composable
fun StreakBadge(
    visible: Boolean,
    streakColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
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
                    border = BorderStrokeDefaults.minimum(streakColor),
                    shape = CircleShape
                )
        ) {
            this@Column.AnimatedVisibility(
                visible = visible,
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

@Preview
@Composable
private fun PreviewStreakBadge(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {

    val item = dayStreakPreview.first()

    AppTheme(themeMode) {
        StreakBadge(true, Color.Red)
    }
}
