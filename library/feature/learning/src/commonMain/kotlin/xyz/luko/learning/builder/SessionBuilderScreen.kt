package xyz.luko.learning.builder

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.designsystem.components.button.AppButton
import xyz.luko.designsystem.components.button.attrs.ButtonRole
import xyz.luko.designsystem.components.button.attrs.ButtonSize
import xyz.luko.designsystem.components.page.BaseScaffold
import xyz.luko.designsystem.preview.PreviewScreen
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.preview.ThemeModeProvider
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.theme.Theme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnDifficultySelected
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnLevelSelected
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnNextPage
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnPreviousPage
import xyz.luko.learning.builder.SessionBuilderScreenEvent.OnQuestionCountSelected
import xyz.luko.learning.builder.SessionBuilderViewModel.Companion.PAGE_COUNT

@Composable
internal fun SessionBuilderScreen() {
    val viewModel = koinViewModel<SessionBuilderViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pager = rememberPagerState(initialPage = state.currentPage) { PAGE_COUNT }

    LaunchedEffect(state.currentPage) {
        if (pager.currentPage != state.currentPage) {
            pager.animateScrollToPage(state.currentPage)
        }
    }

    SessionBuilderScreen(
        state = state,
        pager = pager,
        onEvent = viewModel::onEventReceived
    )
}


@Composable
private fun SessionBuilderScreen(
    state: SessionBuilderViewModel.UiState,
    pager: PagerState,
    onEvent: (SessionBuilderScreenEvent) -> Unit = {}
) {

    BaseScaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            HorizontalPager(
                state = pager,
                userScrollEnabled = false,
                pageSpacing = Padding.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = Padding.large),
            ) { page ->
                when (page) {
                    0 -> {
                        CharaFreqLevelGroupPicker(
                            selectedLevels = state.levels,
                            onClick = { level ->
                                onEvent(OnLevelSelected(level))
                            }
                        )
                    }

                    1 -> {
                        DifficultyPicker(
                            difficulty = state.difficulty,
                            onDifficultySelected = {
                                onEvent(OnDifficultySelected(it))
                            }
                        )
                    }

                    2 -> {
                        QuestionCountPicker(
                            selectedCount = state.questionCount,
                            onCountClicked = {
                                onEvent(OnQuestionCountSelected(it))
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.padding(Padding.large),
                horizontalArrangement = Spacing.medium,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    visible = state.showPreviewButton,
                    modifier = Modifier
                        .weight(1f),
                    enter = slideInHorizontally(),
                    exit = slideOutHorizontally()
                ) {
                    AppButton(
                        text = Theme.strings.previous,
                        role = ButtonRole.Secondary,
                        size = ButtonSize.Large,
                        onClick = {
                            onEvent(OnPreviousPage(pager.currentPage, pager.pageCount))
                        }
                    )
                }

                AppButton(
                    text = if (state.isFinished) Theme.strings.start else Theme.strings.next,
                    size = ButtonSize.Large,
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        onEvent(OnNextPage(pager.currentPage, pager.pageCount))
                    }
                )
            }
        }
    }
}


@PreviewScreen
@Composable
private fun PreviewSessionBuilderScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppTheme(themeMode) {
        SessionBuilderScreen(
            state = SessionBuilderViewModel.UiState(),
            pager = rememberPagerState(initialPage = 0) { PAGE_COUNT }
        )
    }
}