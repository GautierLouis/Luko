package xyz.luko.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.SessionSettings
import xyz.luko.domain.repository.DownloadState
import xyz.luko.home.item.DownloadCard
import xyz.luko.home.item.HeaderItem
import xyz.luko.home.item.LearnItem
import xyz.luko.home.item.NewsItem
import xyz.luko.home.item.PreviouslyItem
import xyz.luko.ui.core.TestTags
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute

@Composable
fun HomeScreen(
    paddingValues: PaddingValues
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(paddingValues, state.value) { viewModel.event(it) }
}

@Composable
private fun HomeScreen(
    internalPadding: PaddingValues = PaddingValues.Zero,
    state: HomeViewModel.UIState,
    onEvent: (HomeScreenEvent) -> Unit = {}
) {

    NestedScaffold(
        modifier = Modifier
            .padding(internalPadding)
            .testTag(TestTags.Screen.HOME),
        snackbarHost = {
            AnimatedVisibility(
                visible = state.isSyncing,
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
                DownloadCard(
                    state = state.syncingState,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    onEvent(HomeScreenEvent.RetrySync)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(key = "header") {
                HeaderItem(state.streakCount)
            }

            item { Spacer(Modifier.height(15.dp)) }

            if (state.isDebug) {
                item(key = "debug_menu") {
                    DevMenuScreen()
                }
            }

            if (state.enableNews) {
                item(key = "news") {
                    NewsItem(state.news, onClick = onEvent)
                }
            }

            item(key = "learn") {
                LearnItem(state.enableLearn, state.lastSettings)
            }

            if (state.enableLastSession) {
                item(key = "previous_sessions") {
                    PreviouslyItem(state.lastSession)
                }
            }
        }
    }
}

@Composable
private fun DevMenuScreen() {
    Button(
        onClick = {
            AppNavigation.navigate(AppRoute.Home.DebugMenu)
        },
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Theme.materialColors.error,
            contentColor = Theme.materialColors.onError
        )
    ) {
        Text("Debug menu")
    }
}

@Preview
@Composable
private fun PreviewHomeScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        HomeScreen(
            state = HomeViewModel.UIState(
                isDebug = false,
                lastSession = PreviewProvider.sessionList.take(5),
                streakCount = 1,
                lastSettings = listOf(
                    SessionSettings(
                        difficultyLevel = DifficultyLevel.HARD,
                        count = 5,
                        frequencyLevel = listOf(CharacterFrequencyLevel.COMMON)
                    )
                ),
                news = listOf(
                    NewCard.Onboarding,
                    NewCard.Dictionary
                ),
                syncingState = DownloadState.Downloaded
            ),
        )
    }
}
