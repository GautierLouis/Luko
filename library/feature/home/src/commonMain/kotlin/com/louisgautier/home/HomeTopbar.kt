package com.louisgautier.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.louisgautier.designsystem.components.topbar.AppTopbar
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme

@Composable
internal fun HomeTopbar(
    greetingMessage: GreetingMessage,
    modifier: Modifier = Modifier,
) {
    AppTopbar(
        title = when (greetingMessage) {
            GreetingMessage.GOOD_MORNING -> Theme.strings.goodMorning
            GreetingMessage.GOOD_AFTERNOON -> Theme.strings.goodAfternoon
            GreetingMessage.GOOD_EVENING -> Theme.strings.goodEvening
            GreetingMessage.WELCOME_BACK -> Theme.strings.welcomeBack
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun PreviewMainTopbar(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        HomeTopbar(GreetingMessage.GOOD_AFTERNOON)
    }
}