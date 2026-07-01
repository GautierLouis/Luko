package xyz.luko.learning.congratulation.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.mp.KoinPlatform.getKoin
import xyz.luko.domain.model.Session
import xyz.luko.learning.navigation.LearningInternalRoute
import xyz.luko.ui.core.TestTags
import xyz.luko.ui.core.adaptive.AdaptiveContainer
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.core.window.rememberIsWiderThanTall
import xyz.luko.ui.core.window.rememberWindowInfo
import xyz.luko.ui.designsystem.components.button.AppButton
import xyz.luko.ui.designsystem.components.button.attrs.ButtonRole
import xyz.luko.ui.designsystem.components.button.attrs.ButtonShape
import xyz.luko.ui.designsystem.components.button.attrs.ButtonSize
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.designsystem.preview.PreviewScreen
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.Spacing
import xyz.luko.ui.navigation.AppNavigation
import xyz.luko.ui.navigation.AppRoute
import xyz.luko.utils.toHHMMSS

@Composable
internal fun CongratulationScreen(
    route: LearningInternalRoute.CongratulationRoute
) {

    val viewModel = koinViewModel<CongratulationViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    CongratulationScreen(
        state = state,
        session = route.lastSession,
        onEvent = {
            getKoin().getScopeOrNull("session_${route.lastSession.id}")?.close()
        }
    )
}

@Composable
internal fun CongratulationScreen(
    state: CongratulationViewModel.UIState,
    session: Session,
    onEvent: () -> Unit = {}
) {

    val isWiderThanTall = rememberIsWiderThanTall()
    val windowInfo = rememberWindowInfo()

    val finishCta: @Composable (Modifier) -> Unit = { modifier: Modifier ->
        AppButton(
            text = Theme.strings.congratulationButtonHome,
            size = ButtonSize.Large,
            modifier = modifier
                .testTag(TestTags.Action.PRIMARY),
            role = ButtonRole.Primary,
            shape = ButtonShape.Filled,
            onClick = {
                onEvent()
                AppNavigation.navigateHome()
            },
        )
    }

    val resultCta: @Composable (Modifier) -> Unit = { modifier: Modifier ->
        AppButton(
            text = Theme.strings.congratulationButtonSeeMore,
            size = ButtonSize.Large,
            role = ButtonRole.Secondary,
            shape = ButtonShape.Outlined,
            modifier = modifier
                .testTag(TestTags.Action.SECONDARY),
            onClick = {
                onEvent()
                AppNavigation.navigate(
                    AppRoute.SessionsRoute.SessionListRoute(session.id),
                    true,
                )
            },
        )
    }

    NestedScaffold { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(Padding.large)
                    .testTag(TestTags.Screen.CONGRATS),
        ) {
            AdaptiveContainer(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Spacing.medium,
                verticalArrangement = Arrangement.spacedBy(
                    space = Padding.large,
                    alignment = Alignment.CenterVertically
                ),
                useRow = windowInfo.isHeightCompact(),
            ) { itemModifier ->
                Column(
                    modifier = itemModifier,
                    verticalArrangement = Arrangement.Center,
                ) {
                    AnimatedRewardIcon(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Spacing.medium,
                    ) {
                        Text(
                            text = Theme.strings.congratulationTitle,
                            style = Theme.typography.titleLarge,
                        )
                        Text(
                            text = Theme.strings.congratulationMessage,
                            style = Theme.typography.bodyLarge,
                        )
                    }
                }
                RewardCard(
                    startAnim = state.startAnim,
                    avgAccuracy = session.accuracy.toFloat(),
                    questionCount = session.questionsCount.toString(),
                    time = session.duration.toHHMMSS(),
                    useRow = isWiderThanTall,
                    modifier = itemModifier,
                )
            }

            AdaptiveContainer(
                useRow = isWiderThanTall,
                horizontalArrangement = Spacing.medium,
                verticalArrangement = Spacing.medium
            ) { itemModifier ->
                if (isWiderThanTall) resultCta(itemModifier) else finishCta(itemModifier)
                if (isWiderThanTall) finishCta(itemModifier) else resultCta(itemModifier)
            }
        }
    }
}

@PreviewScreen
@Composable
private fun PreviewCongratulationScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        CongratulationScreen(
            state = CongratulationViewModel.UIState(false),
            session = PreviewProvider.session,
        )
    }
}
