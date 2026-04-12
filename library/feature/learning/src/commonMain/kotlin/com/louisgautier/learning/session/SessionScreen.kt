package com.louisgautier.learning.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.designsystem.components.button.AppButton
import com.louisgautier.designsystem.components.button.attrs.ButtonRole
import com.louisgautier.designsystem.components.button.attrs.ButtonShape
import com.louisgautier.designsystem.components.button.attrs.ButtonSize
import com.louisgautier.designsystem.components.page.BaseScaffold
import com.louisgautier.designsystem.components.page.ErrorScreen
import com.louisgautier.designsystem.components.page.LoadingScreen
import com.louisgautier.designsystem.preview.AppThemeWrapper
import com.louisgautier.designsystem.preview.LoadingMode
import com.louisgautier.designsystem.preview.LoadingModeProvider
import com.louisgautier.designsystem.preview.ThemeMode
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.designsystem.token.dimens.Padding
import com.louisgautier.designsystem.token.dimens.Spacing
import com.louisgautier.domain.previewDictionaryWithGraphic
import com.louisgautier.learning.session.SessionScreenEvent.Finish
import com.louisgautier.learning.session.SessionScreenEvent.Next
import com.louisgautier.learning.session.SessionScreenEvent.Reload
import com.louisgautier.learning.session.SessionScreenEvent.ToggleLeaveDialog
import com.louisgautier.navigation.AppNavigation
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock

@Composable
internal fun SessionScreen() {
    val viewModel = koinViewModel<SessionViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SessionScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SessionScreen(
    state: SessionViewModel.SessionState,
    onEvent: (SessionScreenEvent) -> Unit = {},
) {
    when (state) {
        SessionViewModel.SessionState.Error -> {
            ErrorScreen { onEvent(Reload) }
        }

        SessionViewModel.SessionState.Loading -> {
            LoadingScreen()
        }

        is SessionViewModel.SessionState.Success -> {
            SessionScreen(
                state = state,
                onEvent = onEvent,
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SessionScreen(
    state: SessionViewModel.SessionState.Success,
    onEvent: (SessionScreenEvent) -> Unit = {},
) {

    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { state.questionsState.size }
    )

    if (state.showLeaveDialog) {
        LeaveSessionDialog(
            onDismissRequest = { onEvent(ToggleLeaveDialog) },
            onConfirmation = { AppNavigation.navigateHome() }
        )
    }

    LaunchedEffect(state.currentPage) {
        pagerState.animateScrollToPage(pagerState.currentPage + 1)
    }

    BaseScaffold(
        topBar = {
            Header(
                pager = pagerState,
                char = state.currentQuestion.question.dictionary,
                modifier = Modifier.padding(top = 32.dp)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(Modifier.weight(1f))
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Padding.large),
                    userScrollEnabled = false,
                    state = pagerState
                ) {
                    GraphicSketcher(
                        state = state.currentQuestion,
                        drawReference = state.drawReference,
                        drawHint = state.drawHint,
                        onEvent = onEvent,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Spacer(Modifier.weight(3f))

                Column(
                    modifier = Modifier.padding(Padding.large),
                    verticalArrangement = Spacing.medium
                ) {
                    AppButton(
                        text = if (state.isLastQuestion) Theme.strings.sessionComplete else Theme.strings.sessionFinish,
                        shape = ButtonShape.Filled,
                        role = ButtonRole.Primary,
                        enabled = state.currentQuestion.isAnswered,
                        size = ButtonSize.Large,
                        onClick = {
                            val event = if (state.isLastQuestion) Next else Finish
                            onEvent(event)
                        },
                    )

                    AppButton(
                        text = Theme.strings.sessionQuit,
                        shape = ButtonShape.Ghost,
                        role = ButtonRole.Error,
                        size = ButtonSize.Large,
                        onClick = { onEvent(ToggleLeaveDialog) },
                    )
                }
            }
        }
    )
}

private val previewSuccessState = SessionViewModel.SessionState.Success(
    startTime = Clock.System.now(),
    currentPage = 0,
    questionsState = listOf(
        SessionViewModel.QuestionState(
            question = previewDictionaryWithGraphic,
            previousDrawnStrokes = emptyList(),
        )
    ),
    drawReference = false,
    drawHint = false,
    showLeaveDialog = false,
)

@Preview
@Composable
private fun PreviewSessionScreenDay(
    @PreviewParameter(LoadingModeProvider::class) mode: LoadingMode
) {
    AppThemeWrapper(ThemeMode.Day) {
        SessionScreen(
            state = when (mode) {
                LoadingMode.ERROR -> SessionViewModel.SessionState.Error
                LoadingMode.SUCCESS -> previewSuccessState
                LoadingMode.LOADING -> SessionViewModel.SessionState.Loading
            }
        )
    }
}

@Preview
@Composable
private fun PreviewSessionScreenNight(
    @PreviewParameter(LoadingModeProvider::class) mode: LoadingMode
) {
    AppThemeWrapper(ThemeMode.Night) {
        SessionScreen(
            state = when (mode) {
                LoadingMode.ERROR -> SessionViewModel.SessionState.Error
                LoadingMode.SUCCESS -> previewSuccessState
                LoadingMode.LOADING -> SessionViewModel.SessionState.Loading
            }
        )
    }
}


