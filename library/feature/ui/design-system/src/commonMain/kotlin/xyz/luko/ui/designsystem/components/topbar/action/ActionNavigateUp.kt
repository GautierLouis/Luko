package xyz.luko.ui.designsystem.components.topbar.action

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.ArrowBack
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.navigation.AppNavigation

@Composable
fun ActionNavigateUp(modifier: Modifier = Modifier) {
    IconButton(
        modifier = modifier,
        onClick = { AppNavigation.navigateUp() },
    ) {
        Icon(
            imageVector = AppIcon.ArrowBack,
            contentDescription = Theme.strings.actionBack,
        )
    }
}

@Preview
@Composable
private fun PreviewActionNavigateUp() {
    ActionNavigateUp()
}
