package xyz.luko.ui.designsystem.components.topbar.action

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.Filter
import xyz.luko.ui.designsystem.theme.Theme

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
