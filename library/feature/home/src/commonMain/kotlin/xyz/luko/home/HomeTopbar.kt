package xyz.luko.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.designsystem.components.topbar.AppTopbar
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme

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