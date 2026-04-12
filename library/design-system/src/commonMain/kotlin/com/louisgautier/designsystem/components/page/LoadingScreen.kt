package com.louisgautier.designsystem.components.page

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
) {
    BaseScaffold(
        modifier = modifier,
    ) { paddingValues ->
        LoadingContent(Modifier.padding(paddingValues).fillMaxHeight())
    }
}

@Preview
@Composable
private fun PreviewLoadingScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        LoadingScreen()
    }
}