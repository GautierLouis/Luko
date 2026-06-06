package xyz.luko.learning.congratulation.stats

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewParameter
import xyz.luko.baseui.adaptive.AdaptiveLayout
import xyz.luko.baseui.adaptive.AdaptiveLayoutOrder
import xyz.luko.baseui.adaptive.AdaptiveLayoutOrientation
import xyz.luko.baseui.device.rememberAdaptiveWindowInfo
import xyz.luko.designsystem.components.button.AppButton
import xyz.luko.designsystem.components.button.attrs.ButtonRole
import xyz.luko.designsystem.components.button.attrs.ButtonShape
import xyz.luko.designsystem.components.button.attrs.ButtonSize
import xyz.luko.designsystem.components.page.NestedScaffold
import xyz.luko.designsystem.preview.PreviewScreen
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.domain.model.Session
import xyz.luko.navigation.AppNavigation
import xyz.luko.navigation.AppRoute
import xyz.luko.ui.core.TestTags
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.utils.toHHMMSS

@Composable
internal fun CongratulationScreen(session: Session) {

    val device = rememberAdaptiveWindowInfo()

    val orientation =
        if (device.isPhoneLandscape) AdaptiveLayoutOrientation.ROW else AdaptiveLayoutOrientation.COLUMN

    NestedScaffold { paddingValues ->
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
                modifier = Modifier
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
                modifier = Modifier.padding(bottom = Padding.large),
                orientation =
                    if (device.isPhoneLandscape || device.isTabletLandscape) {
                        AdaptiveLayoutOrientation.ROW
                    } else {
                        AdaptiveLayoutOrientation.COLUMN
                    },
                order =
                    if (device.isPhoneLandscape || device.isTabletLandscape) {
                        AdaptiveLayoutOrder.REVERSED
                    } else {
                        AdaptiveLayoutOrder.NATURAL
                    },
            ) {

                AppButton(
                    text = Theme.strings.congratulationButtonHome,
                    size = ButtonSize.Large,
                    modifier = Modifier
                        .testTag(TestTags.Action.PRIMARY),
                    role = ButtonRole.Primary,
                    shape = ButtonShape.Filled,
                    onClick = { AppNavigation.navigateHome() },
                )

                AppButton(
                    text = Theme.strings.congratulationButtonSeeMore,
                    size = ButtonSize.Large,
                    role = ButtonRole.Secondary,
                    shape = ButtonShape.Outlined,
                    modifier = Modifier
                        .testTag(TestTags.Action.SECONDARY),
                    onClick = {
                        AppNavigation.navigate(
                            AppRoute.SessionsRoute.SessionListRoute(session.id),
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
private fun PreviewCongratulationScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        CongratulationScreen(
            session = PreviewProvider.session,
        )
    }
}
