package com.louisgautier.dictionary.home

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
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.BorderStrokeDefaults
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.domain.model.SimpleDictionary
import com.louisgautier.domain.previewSimpleDictionary

@Composable
internal fun CharacterItem(
    dictionary: SimpleDictionary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Card(
        modifier = modifier
            .clip(ShapeDefaults.card())
            .clickable(
                role = Role.Button,
                onClickLabel = dictionary.pinyin.first(),
                onClick = onClick,
            ),
        shape = ShapeDefaults.card(),
        colors = CardDefaults.cardColors(
            containerColor = Theme.materialColors.background,
            contentColor = Theme.materialColors.onBackground
        ),
        border = BorderStrokeDefaults.minimum(Theme.materialColors.outline)
    ) {
        Column(
            modifier = Modifier
                .aspectRatio(CharacterItemDefault.RATIO)
                .padding(Padding.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = dictionary.character.toString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Theme.typography.displayMedium
            )

            Text(
                text = dictionary.pinyin.first(),
                style = Theme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCharacterItem(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        CharacterItem(
            previewSimpleDictionary,
            modifier = Modifier.width(CharacterItemDefault.MINIMUM_WIDTH.dp)
        )
    }
}