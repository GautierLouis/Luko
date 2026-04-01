package com.louisgautier.learning.session

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.designsystem.token.typo.FontWeight
import com.louisgautier.domain.model.Dictionary
import com.louisgautier.domain.previewDictionary
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun Header(
    pager: PagerState,
    char: Dictionary,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.large),
        verticalArrangement = Spacing.extraExtraLarge
    ) {
        LinearProgressIndicator(
            progress = { pager.currentPage.toFloat() / pager.pageCount.toFloat() },
            color = Theme.materialColors.tertiary,
            trackColor = Theme.materialColors.tertiaryContainer,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            gapSize = (-10).dp,
            drawStopIndicator = {}
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(
                width = 1.dp,
                color = Theme.materialColors.primary
            ),
            shape = ShapeDefaults.button(),
            colors = CardDefaults.cardColors(
                containerColor = Theme.materialColors.surfaceContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Padding.extraExtraLarge),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Spacing.large,
            ) {
                Text(
                    text = "Draw Chinese character for:",
                    color = Theme.materialColors.onSecondaryContainer,
                    fontWeight = FontWeight.medium,
                    style = Theme.typography.titleSmall
                )
                Card(
                    modifier = Modifier,
                    shape = ShapeDefaults.card(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Theme.materialColors.primary
                    )
                ) {
                    Text(
                        text = char.pinyin.firstOrNull().orEmpty(),
                        color = Theme.materialColors.onPrimary,
                        style = Theme.typography.headlineLarge,
                        modifier = Modifier.padding(
                            horizontal = Padding.large,
                            vertical = Padding.medium
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHeader(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        Header(
            pager = PagerState(currentPage = 4) { 5 },
            char = previewDictionary
        )
    }
}