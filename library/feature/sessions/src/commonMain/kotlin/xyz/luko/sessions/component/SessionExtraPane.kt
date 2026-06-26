package xyz.luko.sessions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.domain.model.SessionResponse
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.ShapeDefaults
import xyz.luko.ui.designsystem.token.dimens.Spacing
import kotlin.math.roundToInt

@Composable
internal fun SessionExtraPane(
    response: SessionResponse,
    similarResponse: List<SessionResponse>,
    contentPadding: PaddingValues = WindowInsets.systemBars.asPaddingValues(),
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.materialColors.background)
            .padding(top = Padding.large)
            .padding(horizontal = Padding.large),
        verticalArrangement = Spacing.large,
        contentPadding = contentPadding
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Spacing.large
            ) {
                DrawableWrapper(data = response.strokes, Type.USER)
                DrawableWrapper(data = response.references, Type.REFERENCE)
            }
        }

        item {
            HorizontalDivider(thickness = 1.dp)
        }

        item {
            Text(
                text = Theme.strings.listExtraStrokeByStroke.uppercase(),
                style = Theme.typography.titleMedium,
                modifier = Modifier.padding(bottom = Padding.medium)
            )

            response.statistics.strokeAccuracies.forEachIndexed { index, value ->
                StrokeItem(index, value)
            }
        }

        item {
            HorizontalDivider(thickness = 1.dp)
        }

        item {
            Text(
                text = Theme.strings.listExtraHistoric.uppercase(),
                style = Theme.typography.titleMedium,
                modifier = Modifier.padding(bottom = Padding.medium)
            )

            Text(
                text = Theme.strings.listExtraHistoricAllTime.uppercase(),
                style = Theme.typography.titleSmall,
                modifier = Modifier.padding(bottom = Padding.medium)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Spacing.medium
            ) {
                val avgAccuracy = similarResponse
                    .map { it.statistics.overallAccuracy }
                    .average()
                    .toFloat()

                val progress =
                    similarResponse.lastOrNull()?.statistics?.overallAccuracy?.let { last ->
                        last - similarResponse.first().statistics.overallAccuracy
                    }

                HistoricAllTime(
                    value = "${similarResponse.size}",
                    label = Theme.strings.listExtraHistoricTimeSeen
                )
                HistoricAllTime(
                    value = "${avgAccuracy.roundToInt()}%",
                    label = Theme.strings.listExtraHistoricAvgAccuracy
                )

                HistoricAllTime(
                    value = progress?.let { "${if (it >= 0) "+" else ""}${it.roundToInt()}%" }
                        ?: "-",
                    label = Theme.strings.listExtraHistoricProgress
                )
            }

            Text(
                text = Theme.strings.listExtraHistoricAccuracyOverTime.uppercase(),
                style = Theme.typography.titleSmall,
                modifier = Modifier.padding(vertical = Padding.medium)
            )

            AccuracyChart(
                accuracies = similarResponse.map { it.statistics.overallAccuracy },
                modifier = Modifier
                    .background(
                        color = Theme.materialColors.surfaceContainer,
                        shape = ShapeDefaults.card(),
                    )
                    .padding(Padding.large)
            )

        }
    }
}

@Preview
@Composable
private fun PreviewSessionExtraPane(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        NestedScaffold {
            SessionExtraPane(
                response = PreviewProvider.sessionResponse.first(),
                similarResponse = PreviewProvider.sessionResponse
            )
        }
    }
}

