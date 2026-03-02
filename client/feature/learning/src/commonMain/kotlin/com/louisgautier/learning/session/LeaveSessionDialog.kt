package com.louisgautier.learning.session

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LeaveSessionDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismissRequest() },
        title = { Text(text = "Are you sure?") },
        text = { Text(text = "If you quit, all XP wil be lost") },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                    onDismissRequest()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Preview
@Composable
fun LeaveSessionDialogPreview() {
    LeaveSessionDialog()

}