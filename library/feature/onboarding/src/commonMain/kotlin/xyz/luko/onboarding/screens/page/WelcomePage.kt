package xyz.luko.onboarding.screens.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.onboarding.strings.onboardingString
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme

@Composable
internal fun WelcomePage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(58.dp)
        ) {
            Text(
                text = Theme.onboardingString.welcomeTitle,
                style = Theme.typography.displayLarge,
                color = Theme.materialColors.onSecondaryContainer,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = Theme.onboardingString.welcomeCaption,
                style = Theme.typography.headlineMedium,
                color = Theme.materialColors.onSecondaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewWelcomePage(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        WelcomePage()
    }
}
