package xyz.luko.learning.congratulation.streak.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import kotlinx.collections.immutable.ImmutableList
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults

@Composable
internal fun StreakWeek(
    days: ImmutableList<StreakDay>,
    startFirstAnim: Boolean,
    modifier: Modifier = Modifier,
) {
    val daysString = Theme.strings.dayOfWeekName

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.materialColors.background)
            .border(
                border = BorderStrokeDefaults.minimum(Theme.materialColors.outline),
                shape = ShapeDefaults.card()
            )
            .padding(vertical = Padding.small),
        contentAlignment = Alignment.BottomStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            days.zip(daysString).forEach { (day, title) ->
                StreakDayItem(
                    title = title,
                    day = day,
                    startFirstAnim = startFirstAnim,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewStreakWeek(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        StreakWeek(
            days = previewStreakDays,
            startFirstAnim = true
        )
    }
}
