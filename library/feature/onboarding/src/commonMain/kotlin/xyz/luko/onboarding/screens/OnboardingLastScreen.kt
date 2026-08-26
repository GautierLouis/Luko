package xyz.luko.onboarding.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.onboarding.strings.onboardingString
import xyz.luko.ui.designsystem.components.button.AppButton
import xyz.luko.ui.designsystem.components.button.attrs.ButtonRole
import xyz.luko.ui.designsystem.components.button.attrs.ButtonShape
import xyz.luko.ui.designsystem.components.button.attrs.ButtonSize
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute


@Composable
internal fun OnboardingLastScreen() {
    NestedScaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.materialColors.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Column {
                AppButton(
                    onClick = {
                        AppNavigation.navigate(AppRoute.Learning.BuildSession, true)
                    },
                    text = Theme.onboardingString.lastPagePracticeNow,
                    size = ButtonSize.Large,
                    role = ButtonRole.Secondary,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                )
                AppButton(
                    onClick = {
                        AppNavigation.navigateHome()
                    },
                    text = Theme.onboardingString.lastPageQuit,
                    size = ButtonSize.Large,
                    role = ButtonRole.Secondary,
                    shape = ButtonShape.Outlined,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                )
            }
        }
    }
}


@Preview
@Composable
private fun PreviewOnboardingLastScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        OnboardingLastScreen()
    }
}
