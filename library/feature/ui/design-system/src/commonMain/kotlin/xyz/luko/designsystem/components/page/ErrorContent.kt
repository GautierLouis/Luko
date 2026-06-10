package xyz.luko.designsystem.components.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.designsystem.components.button.AppButton
import xyz.luko.designsystem.components.button.attrs.ButtonRole
import xyz.luko.designsystem.components.button.attrs.ButtonShape
import xyz.luko.designsystem.components.button.attrs.ButtonSize
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.Refresh
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme

/**
 * Error content for anything else that a full screen.
 * For full screen, see @see[ErrorScreen]
 * This must be used in Scaffold or similar to provide a content color
 */
@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    action: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = Theme.strings.error,
            style = Theme.typography.bodyMedium,
            color = Theme.materialColors.onSurface,
        )
        AppButton(
            text = Theme.strings.retry,
            shape = ButtonShape.Outlined,
            role = ButtonRole.Error,
            size = ButtonSize.Medium,
            onClick = action,
            trailingIcon = AppIcon.Refresh,
        )
    }
}

@Preview
@Composable
private fun PreviewErrorContent(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        ErrorContent()
    }
}
