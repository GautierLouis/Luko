package xyz.luko.onboarding.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.onboarding.screens.page.DrawPage
import xyz.luko.onboarding.screens.page.OnboardingPage
import xyz.luko.onboarding.screens.page.ProgressPage
import xyz.luko.onboarding.screens.page.WelcomePage
import xyz.luko.ui.designsystem.components.PageIndicator
import xyz.luko.ui.designsystem.components.button.AppButton
import xyz.luko.ui.designsystem.components.button.attrs.ButtonRole
import xyz.luko.ui.designsystem.components.button.attrs.ButtonSize
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute

@Composable
internal fun OnboardingScreen() {
    val viewModel = koinViewModel<OnboardingViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    OnboardingScreen(state.dummy)
}

@Composable
private fun OnboardingScreen(
    str: String
) {
    val pagerState = rememberPagerState { OnboardingPage.entries.size }
    val scope = rememberCoroutineScope()

    NestedScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.materialColors.secondaryContainer),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(.8f),
            ) { index ->
                when (OnboardingPage.entries[index]) {
                    OnboardingPage.Welcome -> WelcomePage()
                    OnboardingPage.Draw -> DrawPage()
                    OnboardingPage.Progress -> ProgressPage()
                }
                Box(Modifier.background(Color.Red)) {
                    Text(text = str)
                }
            }

            Spacer(Modifier.weight(1f))

            PageIndicator(
                pagerState,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            AppButton(
                onClick = {
                    if (pagerState.canScrollForward) {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    } else {
                        AppNavigation.navigate(AppRoute.Onboarding.LastPage, true)
                    }
                },
                text = "Next",
                size = ButtonSize.Large,
                role = ButtonRole.Secondary,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp)
            )
        }
    }
}


@Preview
@Composable
private fun PreviewOnboardingScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        OnboardingScreen(
            str = ""
        )
    }
}


