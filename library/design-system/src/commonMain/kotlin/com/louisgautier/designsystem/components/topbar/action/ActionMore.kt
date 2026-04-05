package com.louisgautier.designsystem.components.topbar.action

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ActionFilter(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Rounded.Tune,
            contentDescription = Theme.strings.actionFilter
        )
    }
}

@Preview
@Composable
private fun PreviewActionFilter() {
    ActionFilter()
}