package xyz.luko.designsystem.components.metrics.item

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.designsystem.components.metrics.RoundIcon
import xyz.luko.designsystem.components.metrics.attrs.AppStatistic
import xyz.luko.designsystem.components.metrics.attrs.AppStatistic.Companion.colors
import xyz.luko.designsystem.components.metrics.attrs.AppStatistic.Companion.icon
import xyz.luko.designsystem.components.metrics.attrs.AppStatistic.Companion.label
import xyz.luko.designsystem.components.metrics.attrs.MetricItem
import xyz.luko.designsystem.components.metrics.attrs.RoundIconSize
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Spacing

@Composable
internal fun AppMetricItem(
    item: MetricItem.AppMetric,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Spacing.small,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RoundIcon(
            icon = item.metric.icon(),
            colors = item.metric.colors(),
            size = RoundIconSize.Large,
        )
        Text(
            text = item.value,
            fontWeight = FontWeight.Bold,
            style = Theme.typography.bodyMedium,
            color = Theme.materialColors.onBackground,
        )
        Text(
            text = item.metric.label(),
            style = Theme.typography.bodyMedium,
            color = Theme.materialColors.onBackground,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAppMetricItem(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        AppMetricItem(
            item =
                MetricItem.AppMetric(
                    metric = AppStatistic.Streak,
                    value = "10",
                ),
        )
    }
}
