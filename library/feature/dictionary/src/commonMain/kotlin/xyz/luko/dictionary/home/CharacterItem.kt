package xyz.luko.dictionary.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.BorderStrokeDefaults
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.ShapeDefaults
import xyz.luko.domain.model.SimpleDictionary
import xyz.luko.domain.previewSimpleDictionary

@Composable
internal fun CharacterItem(
    dictionary: SimpleDictionary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        modifier =
            modifier
                .clip(ShapeDefaults.card())
                .clickable(
                    role = Role.Button,
                    onClickLabel = dictionary.pinyin.first(),
                    onClick = onClick,
                ),
        shape = ShapeDefaults.card(),
        colors =
            CardDefaults.cardColors(
                containerColor = Theme.materialColors.background,
                contentColor = Theme.materialColors.onBackground,
            ),
        border = BorderStrokeDefaults.minimum(Theme.materialColors.outline),
    ) {
        Column(
            modifier =
                Modifier
                    .aspectRatio(CharacterItemDefault.RATIO)
                    .padding(Padding.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = dictionary.character.toString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Theme.typography.displayMedium,
            )

            Text(
                text = dictionary.pinyin.first(),
                style = Theme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCharacterItem(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        CharacterItem(
            previewSimpleDictionary,
            modifier = Modifier.width(CharacterItemDefault.MINIMUM_WIDTH.dp),
        )
    }
}
