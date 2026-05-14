package xyz.luko.learning.session

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme

@Composable
internal fun LeaveSessionDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        containerColor = Theme.materialColors.surfaceContainer,
        titleContentColor = Theme.materialColors.onSurface,
        textContentColor = Theme.materialColors.onSurface,
        title = {
            Text(
                text = Theme.strings.leaveDialogTitle,
                style = Theme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = Theme.strings.leaveDialogMessage,
                style = Theme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmation,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(
                    text = Theme.strings.leaveDialogConfirmation
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = Theme.strings.leaveDialogDismiss
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewLeaveSessionDialog(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        LeaveSessionDialog()
    }

}