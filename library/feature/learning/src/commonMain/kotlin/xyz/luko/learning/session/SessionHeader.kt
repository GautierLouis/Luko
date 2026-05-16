package xyz.luko.learning.session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing

@Composable
internal fun SessionHeader(
    pager: PagerState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.large),
        verticalArrangement = Spacing.extraExtraLarge,
    ) {
        LinearProgressIndicator(
            progress = { pager.currentPage.toFloat() / pager.pageCount.toFloat() },
            color = Theme.materialColors.tertiary,
            trackColor = Theme.materialColors.tertiaryContainer,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(Padding.medium),
            gapSize = (-10).dp,
            drawStopIndicator = {},
        )
    }
}

@Preview
@Composable
private fun PreviewSessionHeader(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        SessionHeader(
            pager = PagerState(currentPage = 4) { 5 },
        )
    }
}
