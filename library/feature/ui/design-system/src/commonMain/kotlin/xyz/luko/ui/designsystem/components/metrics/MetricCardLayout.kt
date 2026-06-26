package xyz.luko.ui.designsystem.components.metrics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import xyz.luko.ui.designsystem.components.metrics.attrs.AppStatistic
import xyz.luko.ui.designsystem.components.metrics.attrs.MetricItem
import xyz.luko.ui.designsystem.components.metrics.attrs.SessionStatistic
import xyz.luko.ui.designsystem.components.metrics.item.AppMetricItem
import xyz.luko.ui.designsystem.components.metrics.item.SessionMetricItem
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.RoundedBarChart
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.ShapeDefaults
import xyz.luko.ui.designsystem.token.dimens.Spacing

@Composable
internal fun MetricCardLayout(
    header: @Composable RowScope.() -> Unit,
    items: ImmutableList<MetricItem>,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(Padding.smallest) // room for shadow to breathe
            .shadow(elevation = 8.dp, shape = ShapeDefaults.card())
            .clip(ShapeDefaults.card())
            .clickable(enabled = enable) { onClick() }
    ) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            shape = ShapeDefaults.card(),
            color = Theme.materialColors.surfaceContainer,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(Padding.large),
                verticalArrangement = Spacing.large,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Spacing.medium,
                ) {
                    header()
                }
                MetricRow(items)
            }
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
        items.forEach { item ->
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
