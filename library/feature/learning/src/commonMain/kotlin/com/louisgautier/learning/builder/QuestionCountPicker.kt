package com.louisgautier.learning.builder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.louisgautier.baseui.AdaptiveLayout
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding

@Composable
internal fun QuestionCountPicker(
    selectedCount: QuestionCount,
    modifier: Modifier = Modifier,
    onCountClicked: (QuestionCount) -> Unit = {}
) {

    PickerLayout(
        label = Theme.strings.builderPickQuestionCount,
        modifier = modifier
    ) {
        AdaptiveLayout(
            spacing = Padding.large
        ) {
            QuestionCount.entries.forEach { item ->
                QuestionCountItem(
                    count = item,
                    isSelected = item == selectedCount,
                    onClick = { onCountClicked(item) }
                )
            }
        }
    }
}

@Preview(device = Devices.TABLET)
@Preview(device = Devices.PHONE)
@Composable
private fun PreviewQuestionCountPicker(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        QuestionCountPicker(
            selectedCount = QuestionCount.FIVE,
        )
    }
}