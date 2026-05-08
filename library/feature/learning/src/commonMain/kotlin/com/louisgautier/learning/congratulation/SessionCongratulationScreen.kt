package com.louisgautier.learning.congratulation

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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.baseui.AdaptiveLayout
import com.louisgautier.baseui.AdaptiveLayoutOrder
import com.louisgautier.baseui.AdaptiveLayoutOrientation.COLUMN
import com.louisgautier.baseui.AdaptiveLayoutOrientation.ROW
import com.louisgautier.baseui.device.rememberAdaptiveWindowInfo
import com.louisgautier.designsystem.components.button.AppButton
import com.louisgautier.designsystem.components.button.attrs.ButtonRole
import com.louisgautier.designsystem.components.button.attrs.ButtonShape
import com.louisgautier.designsystem.components.button.attrs.ButtonSize
import com.louisgautier.designsystem.components.page.BaseScaffold
import com.louisgautier.designsystem.components.page.LoadingScreen
import com.louisgautier.designsystem.preview.PreviewScreen
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.previewSession
import com.louisgautier.learning.congratulation.SessionCongratulationViewModel.UIState
import com.louisgautier.navigation.AppNavigation
import com.louisgautier.navigation.AppRoute
import com.louisgautier.utils.toHHMMSS
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SessionCongratulationScreen() {
    val viewModel: SessionCongratulationViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    SessionCongratulationScreen(state)
}

@Composable
private fun SessionCongratulationScreen(
    state: UIState,
) {
    when (state) {
        UIState.Loading -> LoadingScreen()
        is UIState.Success -> SessionCongratulationScreen(state.session)
        else -> {
            /*VM navigate back automatically*/
        }
    }

}

@Composable
private fun SessionCongratulationScreen(
    session: Session
) {

    val device = rememberAdaptiveWindowInfo()

    val orientation =
        if (device.isPhoneLandscape) ROW else COLUMN

    BaseScaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Padding.large)
        ) {
            AdaptiveLayout(
                orientation = orientation,
                modifier = Modifier.weight(1f)
                    .wrapContentHeight()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(Modifier.height(Padding.extraExtraLarge))
                    AnimatedRewardIcon(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(Padding.medium))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Spacing.medium
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
                    parentOrientation = orientation
                )
            }

            AdaptiveLayout(
                orientation = if (device.isPhoneLandscape || device.isTabletLandscape) ROW else
                    COLUMN,
                order = if (device.isPhoneLandscape || device.isTabletLandscape) {
                    AdaptiveLayoutOrder.NATURAL
                } else AdaptiveLayoutOrder.REVERSED
            ) {
                AppButton(
                    text = Theme.strings.congratulationButtonHome,
                    size = ButtonSize.Large,
                    role = ButtonRole.Secondary,
                    shape = ButtonShape.Ghost,
                    onClick = { AppNavigation.navigateHome() }
                )

                AppButton(
                    text = Theme.strings.congratulationButtonRestart,
                    size = ButtonSize.Large,
                    onClick = {
                        AppNavigation.navigate(
                            AppRoute.LearningRoute.NewSessionRoute,
                            true
                        )
                    }
                )
            }
        }
    }
}

@PreviewScreen
@Composable
private fun PreviewSessionCongratulationScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        SessionCongratulationScreen(
            session = previewSession
        )
    }
}