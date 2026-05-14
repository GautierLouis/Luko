package xyz.luko.dictionary.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.Cancel
import xyz.luko.designsystem.icon.Search
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.ShapeDefaults

@Composable
internal fun DictionarySearchBar(textFieldState: TextFieldState) {
    Column(
        modifier =
            Modifier
                .height(TopAppBarDefaults.TopAppBarExpandedHeight),
    ) {
        OutlinedTextField(
            state = textFieldState,
            lineLimits = TextFieldLineLimits.SingleLine,
            shape = ShapeDefaults.button(),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
            keyboardOptions =
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                    showKeyboardOnFocus = true,
                ),
            leadingIcon = {
                Icon(
                    imageVector = AppIcon.Search,
                    contentDescription = null,
                )
            },
            trailingIcon = {
                AnimatedVisibility(textFieldState.text.isNotEmpty()) {
                    IconButton(
                        onClick = { textFieldState.clearText() },
                    ) {
                        Icon(
                            imageVector = AppIcon.Cancel,
                            contentDescription = Theme.strings.actionClear,
                        )
                    }
                }
            },
            placeholder = {
                Text(
                    text = Theme.strings.searchPlaceholder,
                    style = Theme.typography.bodyMedium,
                    color = Theme.materialColors.outline,
                )
            },
            colors =
                TextFieldDefaults.colors(
                    unfocusedContainerColor = Theme.materialColors.surfaceContainer,
                    unfocusedTextColor = Theme.materialColors.onSurfaceVariant,
                    unfocusedIndicatorColor = Theme.materialColors.outline,
                    unfocusedTrailingIconColor = Theme.materialColors.outline,
                    focusedContainerColor = Theme.materialColors.surfaceContainer,
                    focusedTextColor = Theme.materialColors.primary,
                    focusedIndicatorColor = Theme.materialColors.primary,
                    focusedTrailingIconColor = Theme.materialColors.primary,
                ),
        )
    }
}

@Preview
@Composable
private fun PreviewDictionarySearchBar(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        DictionarySearchBar(
            textFieldState = rememberTextFieldState(),
        )
    }
}
