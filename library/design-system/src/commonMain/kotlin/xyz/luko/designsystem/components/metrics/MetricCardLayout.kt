package xyz.luko.designsystem.components.metrics

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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.luko.designsystem.components.metrics.attrs.AppStatistic
import xyz.luko.designsystem.components.metrics.attrs.MetricItem
import xyz.luko.designsystem.components.metrics.attrs.SessionStatistic
import xyz.luko.designsystem.components.metrics.item.AppMetricItem
import xyz.luko.designsystem.components.metrics.item.SessionMetricItem
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.RoundedBarChart
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults
import xyz.luko.designsystem.token.dimens.Spacing

@Composable
internal fun MetricCardLayout(
    header: @Composable RowScope.() -> Unit,
    items: ImmutableList<MetricItem>,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth(),
        shape = ShapeDefaults.card(),
        elevation =
            CardDefaults.elevatedCardElevation(
                defaultElevation = 16.dp,
            ),
        colors =
            CardDefaults.cardColors(
                containerColor = Theme.materialColors.surfaceContainer,
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Padding.large),
            verticalArrangement = Spacing.large,
        ) {
            Column(
                verticalArrangement = Spacing.medium,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Spacing.medium,
                ) {
                    header()
                }
            }
            MetricRow(items)
        }
    }
}

@Composable
private fun MetricRow(items: ImmutableList<MetricItem>) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.large),
        horizontalArrangement = Arrangement.SpaceBetween,
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
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
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
                items =
                    persistentListOf(
                        MetricItem.AppMetric(
                            metric = AppStatistic.Streak,
                            value = "10",
                        ),
                        MetricItem.AppMetric(
                            metric = AppStatistic.Streak,
                            value = "10",
                        ),
                        MetricItem.AppMetric(
                            metric = AppStatistic.Streak,
                            value = "10",
                        ),
                    ),
            )
            MetricCardLayout(
                header = {
                    MetricHeader(
                        title = "Header",
                        icon = AppIcon.RoundedBarChart,
                    )
                },
                items =
                    persistentListOf(
                        MetricItem.SessionMetric(
                            metric = SessionStatistic.QuestionCount,
                            value = "10",
                        ),
                        MetricItem.SessionMetric(
                            metric = SessionStatistic.QuestionCount,
                            value = "10",
                        ),
                        MetricItem.SessionMetric(
                            metric = SessionStatistic.QuestionCount,
                            value = "10",
                        ),
                    ),
            )
        }
    }
}
