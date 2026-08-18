package xyz.luko.app.main

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.luko.ui.designsystem.theme.Theme

@Composable
internal fun StartMenuItem(
    item: MenuItem,
    selected: Boolean,
    modifier: Modifier = Modifier.Companion,
    onItemClick: (MenuItem) -> Unit,
) {
    NavigationRailItem(
        modifier = modifier,
        selected = selected,
        onClick = { onItemClick(item) },
        icon = {
            Icon(
                imageVector = item.icon,
                contentDescription = null
            )
        },
        label = {
            Text(
                text = when (item) {
                    MenuItem.Dictionary -> item.title().take(4) + "."
                    else -> item.title()
                },
                style = Theme.typography.labelMedium,
            )
        }

    )
}
