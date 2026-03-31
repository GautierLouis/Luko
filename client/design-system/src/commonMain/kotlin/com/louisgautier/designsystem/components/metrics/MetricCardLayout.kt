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
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.metrics.attrs.MetricItem
import com.louisgautier.designsystem.components.metrics.item.AppMetricItem
import com.louisgautier.designsystem.components.metrics.item.SessionMetricItem
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.designsystem.token.dimens.Spacing
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun MetricCardLayout(
    header: @Composable RowScope.() -> Unit,
    items: ImmutableList<MetricItem>,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(Padding.medium),
        shape = ShapeDefaults.card(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 16.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Theme.materialColors.surfaceContainer,
        )
    ) {
        Column(
            modifier = Modifier.padding(Padding.large),
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            items.take(3).forEach { item ->
                when (item) {
                    is MetricItem.AppMetric -> {
                        AppMetricItem(
                            item = item,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    is MetricItem.SessionMetric -> {
                        SessionMetricItem(
                            item = item,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

            }
        }
    }
}