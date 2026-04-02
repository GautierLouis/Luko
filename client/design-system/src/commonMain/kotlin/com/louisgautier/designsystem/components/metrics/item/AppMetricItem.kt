package com.louisgautier.designsystem.components.metrics.item

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.louisgautier.designsystem.components.metrics.RoundIcon
import com.louisgautier.designsystem.components.metrics.attrs.AppStatistic
import com.louisgautier.designsystem.components.metrics.attrs.AppStatistic.Companion.colorFamily
import com.louisgautier.designsystem.components.metrics.attrs.AppStatistic.Companion.icon
import com.louisgautier.designsystem.components.metrics.attrs.AppStatistic.Companion.label
import com.louisgautier.designsystem.components.metrics.attrs.MetricItem
import com.louisgautier.designsystem.components.metrics.attrs.RoundIconSize
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Spacing
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun AppMetricItem(
    item: MetricItem.AppMetric,
    modifier: Modifier = Modifier,
) {
    val colorFamily = item.metric.colorFamily()

    Column(
        modifier = modifier,
        verticalArrangement = Spacing.small,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundIcon(
            icon = item.metric.icon(),
            colorFamily = colorFamily,
            size = RoundIconSize.Large
        )
        Text(
            text = item.value,
            fontWeight = FontWeight.Bold,
            style = Theme.typography.bodyMedium,
            color = Theme.materialColors.onBackground
        )
        Text(
            text = item.metric.label(),
            style = Theme.typography.bodyMedium,
            color = Theme.materialColors.onBackground
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun PreviewAppMetricItem(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        AppMetricItem(
            item = MetricItem.AppMetric(
                metric = AppStatistic.Streak,
                value = "10"
            )
        )
    }
}
