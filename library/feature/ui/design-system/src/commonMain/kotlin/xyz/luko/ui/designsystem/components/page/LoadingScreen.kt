package xyz.luko.ui.designsystem.components.page

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    NestedScaffold(
        modifier = modifier,
    ) { paddingValues ->
        LoadingContent(Modifier.padding(paddingValues).fillMaxHeight())
    }
}

@Preview
@Composable
private fun PreviewLoadingScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        LoadingScreen()
    }
}
