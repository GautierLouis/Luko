package xyz.luko.learning.congratulation

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.RoundedBolt
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import kotlin.time.Clock

@Composable
internal fun StreakDayItem(
    progress: Float,
    day: StreakDay,
    modifier: Modifier = Modifier,
) {

    val borderColor = when (day.isCompleted) {
        true -> Theme.materialColors.primary
        false -> Theme.materialColors.outline
    }

    Column(
        modifier = modifier,
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
                    tint = Theme.materialColors.primary,
                    modifier = Modifier.fillMaxSize().padding(Padding.small)
                )
            }
        }
    }
}


@Preview
@Composable
private fun PreviewStreakDayItem(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val data = StreakDay(
        date = today.minus(1, DateTimeUnit.DAY),
        isCompleted = true
    )

    AppTheme(themeMode) {
        Column {
            StreakDayItem(
                progress = 1f,
                day = data.copy(isCompleted = true),
            )

        }
    }
}
