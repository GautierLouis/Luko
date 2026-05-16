package xyz.luko.learning.congratulation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.baseui.AdaptiveLayout
import xyz.luko.baseui.AdaptiveLayoutOrder
import xyz.luko.baseui.AdaptiveLayoutOrientation.COLUMN
import xyz.luko.baseui.AdaptiveLayoutOrientation.ROW
import xyz.luko.baseui.TestTags
import xyz.luko.baseui.device.rememberAdaptiveWindowInfo
import xyz.luko.designsystem.components.button.AppButton
import xyz.luko.designsystem.components.button.attrs.ButtonRole
import xyz.luko.designsystem.components.button.attrs.ButtonShape
import xyz.luko.designsystem.components.button.attrs.ButtonSize
import xyz.luko.designsystem.components.page.BaseScaffold
import xyz.luko.designsystem.components.page.LoadingScreen
import xyz.luko.designsystem.preview.PreviewScreen
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.domain.model.Session
import xyz.luko.domain.previewSession
import xyz.luko.learning.congratulation.SessionCongratulationViewModel.UIState
import xyz.luko.navigation.AppNavigation
import xyz.luko.navigation.AppRoute
import xyz.luko.utils.toHHMMSS

@Composable
internal fun SessionCongratulationScreen() {
    val viewModel: SessionCongratulationViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    SessionCongratulationScreen(state)
}

@Composable
private fun SessionCongratulationScreen(state: UIState) {
    when (state) {
        UIState.Loading -> LoadingScreen()
        is UIState.Success -> SessionCongratulationScreen(state.session)
        else -> {
            // VM navigate back automatically
        }
    }
}

@Composable
internal fun SessionCongratulationScreen(session: Session) {
    val device = rememberAdaptiveWindowInfo()

    val orientation =
        if (device.isPhoneLandscape) ROW else COLUMN

    BaseScaffold { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = Padding.large)
                    .testTag(TestTags.Screen.CONGRATS),
        ) {
            AdaptiveLayout(
                orientation = orientation,
                modifier =
                    Modifier
                        .weight(1f)
                        .wrapContentHeight(),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                ) {
                    Spacer(Modifier.height(Padding.extraExtraLarge))
                    AnimatedRewardIcon(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Spacer(Modifier.height(Padding.medium))
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
                    score = session.score,
                    questionCount = session.questionsCount.toString(),
                    time = session.duration.toHHMMSS(),
                    parentOrientation = orientation,
                )
            }

            AdaptiveLayout(
                orientation =
                    if (device.isPhoneLandscape || device.isTabletLandscape) {
                        ROW
                    } else {
                        COLUMN
                    },
                order =
                    if (device.isPhoneLandscape || device.isTabletLandscape) {
                        AdaptiveLayoutOrder.NATURAL
                    } else {
                        AdaptiveLayoutOrder.REVERSED
                    },
            ) {
                AppButton(
                    text = Theme.strings.congratulationButtonHome,
                    size = ButtonSize.Large,
                    modifier = Modifier.testTag(TestTags.Action.PRIMARY),
                    role = ButtonRole.Secondary,
                    shape = ButtonShape.Ghost,
                    onClick = { AppNavigation.navigateHome() },
                )

                AppButton(
                    text = Theme.strings.congratulationButtonRestart,
                    size = ButtonSize.Large,
                    modifier = Modifier.testTag(TestTags.Action.SECONDARY),
                    onClick = {
                        AppNavigation.navigate(
                            AppRoute.LearningRoute.NewSessionRoute,
                            true,
                        )
                    },
                )
            }
        }
    }
}

@PreviewScreen
@Composable
private fun PreviewSessionCongratulationScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        SessionCongratulationScreen(
            session = previewSession,
        )
    }
}
