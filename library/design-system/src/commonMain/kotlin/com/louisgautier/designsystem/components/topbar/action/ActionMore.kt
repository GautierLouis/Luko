package com.louisgautier.designsystem.components.topbar.action

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.Filter
import com.louisgautier.designsystem.theme.Theme

@Composable
fun ActionFilter(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = AppIcon.Filter,
            contentDescription = Theme.strings.actionFilter,
        )
    }
}

@Preview
@Composable
private fun PreviewActionFilter() {
    ActionFilter()
}
