package com.louisgautier.designsystem.components.page

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.louisgautier.designsystem.components.topbar.AppTopbar
import com.louisgautier.designsystem.components.topbar.action.ActionNavigateUp
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    action: () -> Unit = {}
) {
    BaseScaffold(
        modifier = modifier,
        topBar = {
            AppTopbar(
                title = "",
                leftIcons = {
                    ActionNavigateUp()
                }
            )
        }
    ) { paddingValues ->
        ErrorContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight(),
            action = action
        )
    }
}

@Preview
@Composable
private fun PreviewErrorScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        ErrorScreen()
    }
}