package com.louisgautier.learning.builder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.designsystem.components.button.v2.AppButtonV2
import com.louisgautier.designsystem.components.button.v2.attrs.ButtonSize
import com.louisgautier.designsystem.components.topbar.AppTopbar
import com.louisgautier.designsystem.components.topbar.action.ActionNavigateUp
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.preview.ThemeModeProvider
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.learning.DifficultyAndQuantityContent
import com.louisgautier.learning.builder.SessionBuilderScreenEvent.OnDifficultySelected
import com.louisgautier.learning.builder.SessionBuilderScreenEvent.OnLevelSelected
import com.louisgautier.learning.builder.SessionBuilderScreenEvent.OnNextPage
import com.louisgautier.learning.builder.SessionBuilderScreenEvent.OnQuestionCountSelected
import com.louisgautier.learning.builder.SessionBuilderViewModel.Companion.PAGE_COUNT
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SessionBuilderScreen() {
    val viewModel = koinViewModel<SessionBuilderViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pager = rememberPagerState(initialPage = state.currentPage) { PAGE_COUNT }

    LaunchedEffect(Unit) {
        viewModel.pageAction.collect { page ->
            pager.animateScrollToPage(page)
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

    Scaffold(
        topBar = {
            AppTopbar(
                title = Theme.strings.newSession,
                leftIcons = { ActionNavigateUp() }
            )
        },
        containerColor = Theme.materialColors.background
    ) { paddingValues ->

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
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = Padding.large),
            ) { page ->
                when (page) {
                    0 -> {
                        HSKGroupPicker(
                            selectedLevels = state.levels,
                            onClick = { level ->
                                onEvent(OnLevelSelected(level))
                            }
                        )
                    }

                    1 -> {
                        DifficultyAndQuantityContent(
                            difficulty = state.difficulty,
                            questionCount = state.questionCount,
                            onDifficultySelected = {
                                onEvent(OnDifficultySelected(it))
                            },
                            onQuestionCountSelected = {
                                onEvent(OnQuestionCountSelected(it))
                            }
                        )
                    }
                }
            }

            AppButtonV2(
                text = if (state.isFinished) Theme.strings.start else Theme.strings.newSession,
                size = ButtonSize.Large,
                modifier = Modifier.padding(Padding.large),
                onClick = {
                    onEvent(OnNextPage(pager.currentPage, pager.pageCount))
                }
            )
        }
    }
}

@Composable
@Preview
private fun PreviewSessionBuilderScreen(
    @PreviewParameter(ThemeModeProvider::class) themeMode: ThemeMode
) {
    AppThemeWrapper(themeMode) {
        SessionBuilderScreen(
            state = SessionBuilderViewModel.UiState(),
            pager = rememberPagerState(initialPage = 0) { 2 }
        )
    }
}