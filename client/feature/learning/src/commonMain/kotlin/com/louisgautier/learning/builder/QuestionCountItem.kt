package com.louisgautier.learning.builder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun QuestionCountItem(
    count: QuestionCount,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    val containerColor = if (isSelected) Theme.materialColors.tertiaryContainer
    else Theme.materialColors.surfaceContainer

    val contentColor = if (isSelected) Theme.materialColors.onTertiaryContainer
    else Theme.materialColors.onSurface

    val borderColor = if (isSelected) Theme.materialColors.onTertiaryContainer
    else Theme.materialColors.outline

    val shape = ShapeDefaults.button()

    Box(
        modifier = modifier
            .size(56.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = borderColor,
                ),
                shape = shape
            )
            .background(
                color = containerColor,
                shape = shape
            )
            .clickable(
                onClick = onClick,
                onClickLabel = count.value.toString(),
                role = Role.Checkbox
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.value.toString(),
            color = contentColor,
            modifier = Modifier.padding(
                horizontal = Padding.large,
                vertical = Padding.medium
            )
        )
    }
}

@Composable
@Preview
private fun PreviewQuestionCountItem(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        Column {
            QuestionCountItem(QuestionCount.FIVE)
            QuestionCountItem(QuestionCount.TEN, isSelected = true)
        }
    }
}