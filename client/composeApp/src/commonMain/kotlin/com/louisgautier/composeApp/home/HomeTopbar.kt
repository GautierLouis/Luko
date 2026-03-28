package com.louisgautier.composeApp.home

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopbar(
    greetingMessage: GreetingMessage,
    modifier: Modifier = Modifier.Companion,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = when (greetingMessage) {
                    GreetingMessage.GOOD_MORNING -> Theme.strings.goodMorning
                    GreetingMessage.GOOD_AFTERNOON -> Theme.strings.goodAfternoon
                    GreetingMessage.GOOD_EVENING -> Theme.strings.goodEvening
                    GreetingMessage.WELCOME_BACK -> Theme.strings.welcomeBack
                },
                style = Theme.typography.titleLarge
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Theme.materialColors.background,
            scrolledContainerColor = Color.Companion.Unspecified,
            navigationIconContentColor = Theme.materialColors.onBackground,
            titleContentColor = Theme.materialColors.onBackground,
            actionIconContentColor = Theme.materialColors.onBackground
        )
    )
}

@Preview
@Composable
private fun PreviewMainTopbar(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        HomeTopbar(GreetingMessage.GOOD_AFTERNOON)
    }
}