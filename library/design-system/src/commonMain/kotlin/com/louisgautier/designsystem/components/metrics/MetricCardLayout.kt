package com.louisgautier.designsystem.components.metrics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.metrics.attrs.AppStatistic
import com.louisgautier.designsystem.components.metrics.attrs.MetricItem
import com.louisgautier.designsystem.components.metrics.attrs.SessionStatistic
import com.louisgautier.designsystem.components.metrics.item.AppMetricItem
import com.louisgautier.designsystem.components.metrics.item.SessionMetricItem
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedBarChart
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.designsystem.token.dimens.Spacing
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun MetricCardLayout(
    header: @Composable RowScope.() -> Unit,
    items: ImmutableList<MetricItem>,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = ShapeDefaults.card(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 16.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Theme.materialColors.surfaceContainer,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Padding.large),
            verticalArrangement = Spacing.large
        ) {
            Column(
                verticalArrangement = Spacing.medium
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Spacing.medium
                ) {
                    header()
                }
            }
            MetricRow(items)
        }
    }
}

@Composable
private fun MetricRow(
    items: ImmutableList<MetricItem>
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = Padding.large),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items.take(3).forEach { item ->
            when (item) {
                is MetricItem.AppMetric -> {
                    AppMetricItem(item = item)
                }

                is MetricItem.SessionMetric -> {
                    SessionMetricItem(item = item)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMetricCardLayout(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        Column {
            MetricCardLayout(
                header = {
                    MetricHeader(
                        title = "Header",
                        icon = AppIcon.RoundedBarChart,
                    )
                },
                items = persistentListOf(
                    MetricItem.AppMetric(
                        metric = AppStatistic.Streak,
                        value = "10"
                    ),
                    MetricItem.AppMetric(
                        metric = AppStatistic.Streak,
                        value = "10"
                    ),
                    MetricItem.AppMetric(
                        metric = AppStatistic.Streak,
                        value = "10"
                    )
                ),
            )
            MetricCardLayout(
                header = {
                    MetricHeader(
                        title = "Header",
                        icon = AppIcon.RoundedBarChart,
                    )
                },
                items = persistentListOf(
                    MetricItem.SessionMetric(
                        metric = SessionStatistic.QuestionCount,
                        value = "10"
                    ),
                    MetricItem.SessionMetric(
                        metric = SessionStatistic.QuestionCount,
                        value = "10"
                    ),
                    MetricItem.SessionMetric(
                        metric = SessionStatistic.QuestionCount,
                        value = "10"
                    ),
                )
            )
        }
    }
}