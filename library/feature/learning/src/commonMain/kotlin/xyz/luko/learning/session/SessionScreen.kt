package xyz.luko.learning.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import org.koin.compose.viewmodel.koinViewModel
import xyz.luko.baseui.device.rememberAdaptiveWindowInfo
import xyz.luko.designsystem.components.page.BaseScaffold
import xyz.luko.designsystem.components.page.ErrorScreen
import xyz.luko.designsystem.components.page.LoadingScreen
import xyz.luko.designsystem.preview.LoadingMode
import xyz.luko.designsystem.preview.LoadingModeProvider
import xyz.luko.designsystem.preview.PreviewScreen
import xyz.luko.designsystem.preview.ThemeMode
import xyz.luko.designsystem.theme.AppTheme
import xyz.luko.designsystem.token.dimens.Padding
import xyz.luko.designsystem.token.dimens.Spacing
import xyz.luko.domain.previewDictionaryWithGraphic
import xyz.luko.learning.session.SessionScreenEvent.Reload
import xyz.luko.learning.session.SessionScreenEvent.ToggleLeaveDialog
import xyz.luko.navigation.AppNavigation
import kotlin.time.Clock

@Composable
internal fun SessionScreen() {
    val viewModel = koinViewModel<SessionViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SessionScreen(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

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

@Composable
private fun SessionScreen(
    state: SessionViewModel.SessionState.Success,
    onEvent: (SessionScreenEvent) -> Unit = {},
) {
    val pagerState =
        rememberPagerState(
            initialPage = state.currentPage,
            pageCount = { state.questionsState.size },
        )

    if (state.showLeaveDialog) {
        LeaveSessionDialog(
            onDismissRequest = { onEvent(ToggleLeaveDialog) },
            onConfirmation = {
                onEvent(ToggleLeaveDialog)
                AppNavigation.navigateHome()
            },
        )
    }

    val device = rememberAdaptiveWindowInfo()

    LaunchedEffect(state.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            pagerState.animateScrollToPage(state.currentPage)
        }
    }

    val backState =
        rememberNavigationEventState(
            currentInfo = NavigationEventInfo.None,
        )

    NavigationBackHandler(
        state = backState,
        isBackEnabled = !state.showLeaveDialog,
        onBackCompleted = { onEvent(ToggleLeaveDialog) },
    )

    BaseScaffold(
        topBar = {
            SessionHeader(
                pager = pagerState,
                modifier =
                    Modifier
                        .statusBarsPadding()
                        .padding(top = Padding.extraLarge),
            )
        },
        content = { paddingValues ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(Padding.large),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                if (device.isPhoneLandscape) {
                    Row(
                        horizontalArrangement = Spacing.large,
                    ) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxHeight()
                                    .weight(1f),
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            PinyinCharacter(
                                char = state.currentQuestion.question.dictionary,
                                modifier = Modifier.wrapContentHeight(),
                            )
                            FooterAction(
                                isLastQuestion = state.isLastQuestion,
                                isAnswered = state.currentQuestion.isAnswered,
                                onEvent = onEvent,
                            )
                        }

                        Pager(
                            pagerState = pagerState,
                            state = state,
                            onEvent = onEvent,
                            modifier = Modifier.weight(1f),
                        )
                    }
                } else {
                    Column(
                        verticalArrangement = Spacing.large,
                    ) {
                        PinyinCharacter(
                            char = state.currentQuestion.question.dictionary,
                            modifier = Modifier.wrapContentHeight(),
                        )
                        Pager(
                            pagerState = pagerState,
                            state = state,
                            onEvent = onEvent,
                            modifier = Modifier.weight(1f),
                        )
                        FooterAction(
                            isLastQuestion = state.isLastQuestion,
                            isAnswered = state.currentQuestion.isAnswered,
                            onEvent = onEvent,
                        )
                    }
                }
            }
        },
    )
}

private val previewSuccessState =
    SessionViewModel.SessionState.Success(
        startTime = Clock.System.now(),
        currentPage = 0,
        questionsState =
            listOf(
                SessionViewModel.QuestionState(
                    question = previewDictionaryWithGraphic,
                    previousDrawnStrokes = emptyList(),
                ),
            ),
        drawReference = false,
        drawHint = false,
        showLeaveDialog = false,
    )

@Preview
@Composable
private fun PreviewSessionScreenDay(
    @PreviewParameter(LoadingModeProvider::class) mode: LoadingMode,
) {
    AppTheme(ThemeMode.Day) {
        SessionScreen(
            state =
                when (mode) {
                    LoadingMode.ERROR -> SessionViewModel.SessionState.Error
                    LoadingMode.SUCCESS -> previewSuccessState
                    LoadingMode.LOADING -> SessionViewModel.SessionState.Loading
                },
        )
    }
}

@PreviewScreen
@Composable
private fun PreviewSessionScreenNight(
    @PreviewParameter(LoadingModeProvider::class) mode: LoadingMode,
) {
    AppTheme(ThemeMode.Night) {
        SessionScreen(
            state =
                when (mode) {
                    LoadingMode.ERROR -> SessionViewModel.SessionState.Error
                    LoadingMode.SUCCESS -> previewSuccessState
                    LoadingMode.LOADING -> SessionViewModel.SessionState.Loading
                },
        )
    }
}
