package xyz.luko.learning.congratulation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.baseui.test.TestTags
import xyz.luko.designsystem.components.button.AppButton
import xyz.luko.designsystem.components.button.attrs.ButtonSize
import xyz.luko.designsystem.components.page.NestedScaffold
import xyz.luko.designsystem.preview.PreviewScreen
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing

@Composable
fun StreakRefreshScreen(
    newStreak: Int,
    onClick: () -> Unit = {},
) {
    val viewModel = koinViewModel<StreakRefreshViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    StreakRefreshScreen(
        state = state,
        newStreak = newStreak,
        onClick = onClick
    )
}

@Composable
private fun StreakRefreshScreen(
    state: StreakRefreshViewModel.UIState,
    newStreak: Int,
    onClick: () -> Unit = {},
) {
    val scale by animateFloatAsState(
        targetValue = if (state.showNew) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )

    NestedScaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Padding.extraExtraLarge)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Spacing.extraExtraLarge,
            ) {

                Text(
                    text = "🔥",
                    style = Theme.typography.displayLarge,
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = if (state.showNew) scale else 1f; scaleY =
                            if (state.showNew) scale else 1f
                        }
                )

                Box(
                    modifier = Modifier.height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    this@Column.AnimatedVisibility(
                        visible = !state.showNew,
                        exit = slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(350)
                        ) + fadeOut(tween(350))
                    ) {
                        Text(
                            text = "${newStreak.dec()}",
                            style = Theme.typography.displayLarge,
                            fontWeight = FontWeight.Medium,
                        )
                    }

                    this@Column.AnimatedVisibility(
                        visible = state.showNew,
                        enter = slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(350, easing = FastOutSlowInEasing)
                        ) + fadeIn(tween(350))
                    ) {
                        Text(
                            text = "$newStreak",
                            style = Theme.typography.displayLarge,
                            fontWeight = FontWeight.Medium,
                            color = Theme.appLevelColors.easy.primary
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = state.showBtn,
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                enter = fadeIn(tween(350))
            ) {
                AppButton(
                    text = Theme.strings.sessionStreakRefreshButtonNext,
                    size = ButtonSize.Large,
                    onClick = onClick,
                    modifier = Modifier.testTag(TestTags.Action.PRIMARY)
                )
            }
        }
    }
}

@PreviewScreen
@Composable
private fun PreviewSteakRefreshScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        StreakRefreshScreen(
            state = StreakRefreshViewModel.UIState(
                showBtn = true,
                showNew = true
            ),
            newStreak = 2
        )
    }
}
