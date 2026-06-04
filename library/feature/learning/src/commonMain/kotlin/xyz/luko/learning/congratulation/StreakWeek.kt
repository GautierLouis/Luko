package xyz.luko.learning.congratulation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import kotlinx.collections.immutable.ImmutableList
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults
import xyz.luko.designsystem.token.dimens.Spacing

@Composable
internal fun StreakWeek(
    days: ImmutableList<StreakDay>,
    modifier: Modifier = Modifier
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
                Column(
                    verticalArrangement = Spacing.small,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
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

                    StreakDayItem(
                        day = day,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
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
        )
    }
}
