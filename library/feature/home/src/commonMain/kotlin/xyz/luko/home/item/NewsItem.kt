package xyz.luko.home.item

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import xyz.luko.home.HomeScreenEvent
import xyz.luko.home.NewCard
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme

@Composable
internal fun NewsItem(
    cards: List<NewCard>,
    onClick: (HomeScreenEvent) -> Unit = {}
) {
    HomeHorizonalPager(
        items = cards,
        modifier = Modifier.height(150.dp)
    ) { index ->
        when (cards[index]) {
            is NewCard.Onboarding -> OnboardingCard(
                modifier = Modifier.fillMaxSize(),
                onClick = { onClick(HomeScreenEvent.StartOnboarding) }
            )

            is NewCard.Dictionary -> DictionaryCard(
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewNewsItem(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        NewsItem(
            listOf(
                NewCard.Onboarding
            )
        ) { }
    }
}

