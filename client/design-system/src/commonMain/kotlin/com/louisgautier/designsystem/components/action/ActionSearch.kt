package com.louisgautier.designsystem.components.action

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louisgautier.designsystem.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ActionSearch(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = Theme.strings.actionSearch
        )
    }
}

@Preview
@Composable
private fun PreviewActionSearch() {
    ActionSearch()
}