package xyz.luko.designsystem.components.topbar.action

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.Search
import xyz.luko.designsystem.theme.Theme

@Composable
fun ActionSearch(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = AppIcon.Search,
            contentDescription = Theme.strings.actionSearch,
        )
    }
}

@Preview
@Composable
private fun PreviewActionSearch() {
    ActionSearch()
}
