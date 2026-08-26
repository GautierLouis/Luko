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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
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
    route: AppRoute.Learning.Congratulation
) {

    val viewModel = koinViewModel<CongratulationViewModel> {
        parametersOf(route)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    CongratulationScreen(state)
}

@Composable
internal fun CongratulationScreen(
    state: CongratulationViewModel.UIState,
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
                AppNavigation.navigate(
                    AppRoute.Sessions.List(state.session.id),
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
                    avgAccuracy = state.session.accuracy.toFloat(),
                    questionCount = state.session.questionsCount.toString(),
                    time = state.session.duration.toHHMMSS(),
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

@Preview
@Composable
private fun PreviewCongratulationScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        CongratulationScreen(
            state = CongratulationViewModel.UIState(
                startAnim = false,
                session = PreviewProvider.session
            ),
        )
    }
}
