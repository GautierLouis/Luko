package xyz.luko.designsystem.components.topbar.action

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.ArrowBack
import xyz.luko.designsystem.theme.Theme
import xyz.luko.navigation.AppNavigation

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
