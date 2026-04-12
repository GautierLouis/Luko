package com.louisgautier.learning.congratulation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.designsystem.components.button.AppButton
import com.louisgautier.designsystem.components.button.attrs.ButtonRole
import com.louisgautier.designsystem.components.button.attrs.ButtonShape
import com.louisgautier.designsystem.components.button.attrs.ButtonSize
import com.louisgautier.designsystem.components.page.BaseScaffold
import com.louisgautier.designsystem.components.topbar.AppTopbar
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.AppTheme
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.Spacing
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

    val session = state.session!!

    BaseScaffold(
        topBar = { AppTopbar("") },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Padding.large)
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

            Spacer(Modifier.height(Padding.large))

            RewardCard(
                score = session.score,
                questionCount = session.questionsCount.toString(),
                time = session.duration.toHHMMSS()
            )

            Spacer(Modifier.weight(1f))

            Column(
                verticalArrangement = Spacing.large
            ) {
                AppButton(
                    text = Theme.strings.congratulationButtonRestart,
                    size = ButtonSize.Large,
                    onClick = {
                        AppNavigation.navigate(
                            AppRoute.LearningRoute.NewSessionRoute,
                            true
                        )
                    },
                )

                AppButton(
                    text = Theme.strings.congratulationButtonHome,
                    size = ButtonSize.Large,
                    role = ButtonRole.Secondary,
                    shape = ButtonShape.Ghost,
                    onClick = { AppNavigation.navigateHome() },
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSessionCongratulationScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        SessionCongratulationScreen(
            state = UIState(
                session = previewSession
            )
        )
    }
}