package xyz.luko.designsystem.components.page

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.designsystem.components.topbar.AppTopbar
import xyz.luko.designsystem.components.topbar.action.ActionNavigateUp
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    action: () -> Unit = {},
) {
    BaseScaffold(
        modifier = modifier,
        topBar = {
            AppTopbar(
                title = "",
                leftIcons = {
                    ActionNavigateUp()
                },
            )
        },
    ) { paddingValues ->
        ErrorContent(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .fillMaxHeight(),
            action = action,
        )
    }
}

@Preview
@Composable
private fun PreviewErrorScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        ErrorScreen()
    }
}
