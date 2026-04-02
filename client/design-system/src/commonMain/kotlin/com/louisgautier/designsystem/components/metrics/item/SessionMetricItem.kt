package com.louisgautier.designsystem.components.metrics.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.metrics.attrs.MetricItem
import com.louisgautier.designsystem.components.metrics.attrs.SessionStatistic
import com.louisgautier.designsystem.components.metrics.attrs.SessionStatistic.Companion.icon
import com.louisgautier.designsystem.components.metrics.attrs.SessionStatistic.Companion.label
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Spacing
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun SessionMetricItem(
    item: MetricItem.SessionMetric,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = Theme.materialColors.surfaceContainer,
            ),
        verticalArrangement = Spacing.medium
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Spacing.medium
        ) {
            Icon(
                imageVector = item.metric.icon(),
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = Theme.materialColors.onBackground
            )
            Text(
                text = item.metric.label(),
                color = Theme.materialColors.onBackground,
                fontWeight = FontWeight.SemiBold,
                style = Theme.typography.bodyMedium,
            )
        }
        Text(
            text = item.value,
            color = Theme.materialColors.onBackground,
            fontWeight = FontWeight.SemiBold,
            style = Theme.typography.titleLarge,
        )
    }
}

@Preview
@Composable
private fun PreviewSessionValue(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        SessionMetricItem(
            item = MetricItem.SessionMetric(
                metric = SessionStatistic.Difficulty,
                value = "Hard"
            )
        )
    }
}