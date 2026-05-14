package xyz.luko.learning.session

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.designsystem.token.typo.FontWeight
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.previewDictionary

@Composable
internal fun PinyinCharacter(
    char: Dictionary,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
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
                text = Theme.strings.sessionHeaderTitle,
                color = Theme.materialColors.onSecondaryContainer,
                fontWeight = FontWeight.medium,
                style = Theme.typography.titleSmall
            )
            Card(
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

@Preview
@Composable
private fun PreviewPinyinCharacter(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        PinyinCharacter(
            char = previewDictionary
        )
    }
}