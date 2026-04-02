package com.louisgautier.learning.congratulation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.designsystem.components.button.v2.AppButtonV2
import com.louisgautier.designsystem.components.button.v2.attrs.ButtonRole
import com.louisgautier.designsystem.components.button.v2.attrs.ButtonShape
import com.louisgautier.designsystem.components.button.v2.attrs.ButtonSize
import com.louisgautier.designsystem.components.topbar.AppTopbar
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.domain.previewSession
import com.louisgautier.learning.congratulation.SessionCongratulationViewModel.UIState
import com.louisgautier.navigation.AppNavigation
import com.louisgautier.navigation.BuilderKey
import com.louisgautier.utils.toHHMMSS
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SessionCongratulationScreen() {
    val viewModel: SessionCongratulationViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    SessionCongratulationScreen(state)
}

@Composable
private fun SessionCongratulationScreen(
    state: UIState,
) {

    val session = state.session!!

    Scaffold(
        topBar = { AppTopbar("") },
        containerColor = Theme.materialColors.background,
        contentColor = Theme.materialColors.onBackground,
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
                AppButtonV2(
                    text = Theme.strings.congratulationButtonRestart,
                    size = ButtonSize.Large,
                    onClick = { AppNavigation.navigate(BuilderKey, true) },
                )

                AppButtonV2(
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
    AppThemeWrapper(themeMode) {
        SessionCongratulationScreen(
            state = UIState(
                session = previewSession
            )
        )
    }
}