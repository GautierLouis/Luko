package com.louisgautier.learning.session

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.ai.Gray400
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.Reset
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.ShapeDefaults
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.domain.previewDictionaryWithGraphic
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
internal fun ResetButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(38.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Theme.materialColors.tertiaryContainer,
            contentColor = Theme.materialColors.onTertiaryContainer
        ),
        shape = ShapeDefaults.button()
    ) {
        Icon(
            imageVector = AppIcon.Reset,
            contentDescription = Theme.strings.actionReset,
            modifier = Modifier
                .padding(Padding.medium)
                .graphicsLayer { scaleX = -1f }
        )
    }
}

@Preview
@Composable
private fun PreviewResetButton(
    @PreviewParameter(ThemeModeProvider ::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        ResetButton()
    }
}