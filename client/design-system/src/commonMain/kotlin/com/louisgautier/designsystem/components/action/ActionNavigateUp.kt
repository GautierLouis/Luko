package com.louisgautier.designsystem.components.action

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.navigation.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ActionNavigateUp(
    modifier: Modifier = Modifier,
) {
    IconButton(
        modifier = modifier,
        onClick = { AppNavigation.navigateUp() }
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = Theme.strings.actionBack
        )
    }
}

@Composable
@Preview
private fun PreviewActionNavigateUp() {
    ActionNavigateUp()
}