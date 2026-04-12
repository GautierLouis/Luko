package com.louisgautier.learning.congratulation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.metrics.attrs.MetricItem
import com.louisgautier.designsystem.components.metrics.attrs.SessionStatistic
import com.louisgautier.designsystem.components.metrics.attrs.SessionStatistic.Companion.icon
import com.louisgautier.designsystem.components.metrics.attrs.SessionStatistic.Companion.label
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.designsystem.token.dimens.Spacing

@Composable
internal fun CurrentSessionMetric(
    item: MetricItem.SessionMetric,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Theme.materialColors.secondaryContainer,
                shape = ShapeDefaults.button()
            )
            .padding(Padding.large),
        verticalArrangement = Spacing.medium
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.metric.icon(),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Theme.materialColors.onSecondaryContainer
            )
            Spacer(Modifier.width(4.dp))

            Text(
                text = item.metric.label(),
                color = Theme.materialColors.onSecondaryContainer,
                style = Theme.typography.bodyLarge
            )
        }

        Text(
            text = item.value,
            color = Theme.materialColors.onSecondaryContainer,
            style = Theme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PreviewCurrentSessionMetric(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        CurrentSessionMetric(
            item = MetricItem.SessionMetric(
                metric = SessionStatistic.Difficulty,
                value = "Hard"
            )
        )
    }
}