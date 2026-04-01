package com.louisgautier.designsystem.components.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.components.button.v2.AppButtonV2
import com.louisgautier.designsystem.components.button.v2.attrs.ButtonRole
import com.louisgautier.designsystem.components.button.v2.attrs.ButtonShape
import com.louisgautier.designsystem.components.button.v2.attrs.ButtonSize
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Spacing
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

/**
 * Error content for anything else that a full screen.
 * For full screen, see @see[ErrorScreen]
 * This must be used in Scaffold or similar to provide a content color
 */
@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    action: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = Theme.strings.error,
            style = Theme.typography.bodyMedium,
            color = Theme.materialColors.onSurface
        )
        AppButtonV2(
            text = Theme.strings.retry,
            shape = ButtonShape.Outlined,
            role = ButtonRole.Error,
            size = ButtonSize.Medium,
            onClick = action,
            trailingIcon = Icons.Default.Refresh
        )
    }
}

@Preview
@Composable
private fun PreviewErrorContent(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        ErrorContent()
    }
}