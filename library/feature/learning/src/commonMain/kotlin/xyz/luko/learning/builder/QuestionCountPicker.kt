package xyz.luko.learning.builder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme

@Composable
internal fun QuestionCountPicker(
    selectedCount: QuestionCount,
    modifier: Modifier = Modifier,
    onClick: (QuestionCount) -> Unit = {},
) {
    PickerLayout(
        label = Theme.strings.builderPickQuestionCount,
        modifier = modifier,
    ) {
        OrientationGrid(
            data = QuestionCount.entries,
            key = { item -> item.ordinal }
        ) { item ->
            QuestionCountItem(
                count = item,
                isSelected = item == selectedCount,
                onClick = { onClick(item) },
            )
        }
    }
}

@Preview(device = Devices.TABLET)
@Preview(device = Devices.PHONE)
@Composable
private fun PreviewQuestionCountPicker(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        QuestionCountPicker(
            selectedCount = QuestionCount.FIVE,
        )
    }
}
