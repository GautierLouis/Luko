package com.louisgautier.designsystem.components.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Failed to load",
            style = Theme.typography.bodyMedium
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Button(
            modifier = Modifier,
            onClick = action
        ) {
            Text(
                text = "Retry",
                style = Theme.typography.labelLarge
            )
        }
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