package xyz.luko.learning.builder

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.learning.builder.SessionBuilderViewModel.Companion.PAGE_COUNT
import xyz.luko.ui.core.TestTags
import xyz.luko.ui.designsystem.components.button.AppButton
import xyz.luko.ui.designsystem.components.button.attrs.ButtonRole
import xyz.luko.ui.designsystem.components.button.attrs.ButtonShape
import xyz.luko.ui.designsystem.components.button.attrs.ButtonSize
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.designsystem.modifier.sharedBounds
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.preview.ThemeModeProvider
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.Spacing

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

    if (state.useAlternativeLayout) {
        SessionBuilderScreenB(
            state = state,
            onEvent = viewModel::onEventReceived
        )
    } else {
        SessionBuilderScreenA(
            state = state,
            pager = pager,
            onEvent = viewModel::onEventReceived,
        )
    }

}

@Composable
private fun SessionBuilderScreenA(
    state: SessionBuilderViewModel.UiState,
    pager: PagerState,
    onEvent: (SessionBuilderScreenEvent) -> Unit = {},
) {
    NestedScaffold { paddingValues ->
        Column(
            modifier =
                Modifier
                    .testTag(TestTags.Screen.SESSION_BUILDER)
                    .sharedBounds("start_session")
                    .padding(paddingValues)
                    .padding(bottom = Padding.large)
                    .fillMaxSize(),
        ) {
            HorizontalPager(
                state = pager,
                userScrollEnabled = false,
                pageSpacing = Padding.large,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = Padding.large),
            ) { page ->
                when (page) {
                    0 -> {
                        CharaFreqLevelGroupPicker(
                            selectedLevels = state.levels,
                            onClick = { level ->
                                onEvent(SessionBuilderScreenEvent.OnFrequencySelected(level))
                            },
                        )
                    }

                    1 -> {
                        DifficultyPicker(
                            difficulty = state.difficulty,
                            onClick = {
                                onEvent(SessionBuilderScreenEvent.OnDifficultySelected(it))
                            },
                        )
                    }

                    2 -> {
                        QuestionCountPicker(
                            selectedCount = state.questionCount,
                            onClick = {
                                onEvent(SessionBuilderScreenEvent.OnQuestionCountSelected(it))
                            },
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.padding(Padding.large),
                horizontalArrangement = Spacing.medium,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnimatedVisibility(
                    visible = state.showPreviewButton,
                    modifier = Modifier.weight(1f),
                    enter = slideInHorizontally(),
                    exit = slideOutHorizontally(),
                ) {
                    AppButton(
                        text = Theme.strings.previous,
                        role = ButtonRole.Secondary,
                        size = ButtonSize.Large,
                        onClick = {
                            onEvent(
                                SessionBuilderScreenEvent.OnPreviousPage(
                                    pager.currentPage,
                                    pager.pageCount
                                )
                            )
                        },
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    if (state.levels.isEmpty()) {
                        Text(
                            text = Theme.strings.builderNoFrequencySelectedError,
                            style = Theme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                            color = Theme.materialColors.error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    AppButton(
                        text = if (state.isFinished) Theme.strings.start else Theme.strings.next,
                        size = ButtonSize.Large,
                        enabled = state.levels.isNotEmpty(),
                        modifier = Modifier.testTag(TestTags.Action.PRIMARY),
                        onClick = {
                            onEvent(
                                SessionBuilderScreenEvent.OnNextPage(
                                    pager.currentPage,
                                    pager.pageCount
                                )
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun SessionBuilderScreenB(
    state: SessionBuilderViewModel.UiState,
    onEvent: (SessionBuilderScreenEvent) -> Unit = {},
) {
    NestedScaffold(
        bottomBar = {
            Column(
                modifier = Modifier.padding(Padding.large),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                AppButton(
                    text = Theme.strings.start,
                    size = ButtonSize.Large,
                    enabled = state.levels.isNotEmpty(),
                    modifier = Modifier
                        .testTag(TestTags.Action.PRIMARY),
                    onClick = {
                    },
                )

                AppButton(
                    text = Theme.strings.previous,
                    role = ButtonRole.Error,
                    size = ButtonSize.Large,
                    shape = ButtonShape.Ghost,
                    onClick = {

                    },
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .testTag(TestTags.Screen.SESSION_BUILDER)
                .sharedBounds("start_session")
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            item(key = "frequency") {
                FrequencyItem(state) {
                    onEvent(SessionBuilderScreenEvent.OnFrequencySelected(it))
                }
            }

            item(key = "difficulty") {
                DifficultyItem(state) {
                    onEvent(SessionBuilderScreenEvent.OnDifficultySelected(it))
                }
            }

            item(key = "count") {
                CountItem(state)
            }
        }
    }
}


@Preview
@Composable
private fun PreviewSessionBuilderScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode,
) {
    AppTheme(themeMode) {
        SessionBuilderScreenA(
            state = SessionBuilderViewModel.UiState(),
            pager = rememberPagerState(initialPage = 0) { PAGE_COUNT },
        )
    }
}
