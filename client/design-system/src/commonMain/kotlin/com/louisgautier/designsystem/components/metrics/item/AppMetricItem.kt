package com.louisgautier.designsystem.components.metrics.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.metrics.attrs.AppStatistic
import com.louisgautier.designsystem.components.metrics.attrs.AppStatistic.Companion.colorFamily
import com.louisgautier.designsystem.components.metrics.attrs.AppStatistic.Companion.icon
import com.louisgautier.designsystem.components.metrics.attrs.AppStatistic.Companion.label
import com.louisgautier.designsystem.components.metrics.attrs.MetricItem
import com.louisgautier.designsystem.components.metrics.RoundIcon
import com.louisgautier.designsystem.components.metrics.attrs.RoundIconSize
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun AppMetricItem(
    item: MetricItem.AppMetric,
    modifier: Modifier = Modifier,
) {
    val colorFamily = item.metric.colorFamily()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
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
