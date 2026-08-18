package xyz.luko.app.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import xyz.luko.ui.designsystem.theme.Theme

@Composable
internal fun RowScope.BottomMenuItem(
    item: MenuItem,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onItemClick: (MenuItem) -> Unit,
) {

    val contentColor =
        if (selected) Theme.materialColors.tertiary else Theme.materialColors.onSurface
    val iconSize = if (selected) 24.dp else 24.dp * 0.8f

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .weight(1f)
            .fillMaxHeight()
            .navigationBarsPadding()
            .clickable(
                enabled = true,
                onClick = { onItemClick(item) },
                onClickLabel = item.title(),
                role = Role.Tab,
                indication = null,
                interactionSource = MutableInteractionSource()
            )
    ) {
        Icon(
            imageVector = item.icon,
            modifier = Modifier.size(iconSize),
            contentDescription = null, //TODO
            tint = contentColor
        )
        Text(
            text = item.title(),
            style = Theme.typography.labelMedium,
            color = contentColor
        )
    }
}
