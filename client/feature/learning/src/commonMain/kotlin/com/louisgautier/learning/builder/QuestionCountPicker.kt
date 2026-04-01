package com.louisgautier.learning.builder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
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

@Composable
@Preview
private fun PreviewQuestionCountPicker(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        QuestionCountPicker(
            selectedCount = QuestionCount.FIVE,
        )
    }
}