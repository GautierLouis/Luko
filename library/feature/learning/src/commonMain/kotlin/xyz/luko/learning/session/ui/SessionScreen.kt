package xyz.luko.learning.session.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import xyz.luko.learning.navigation.LearningInternalRoute
import xyz.luko.learning.session.SessionViewModel
import xyz.luko.learning.session.model.DrawingPageState
import xyz.luko.learning.session.model.SessionScreenEvent
import xyz.luko.learning.session.model.SessionScreenEvent.Reload
import xyz.luko.learning.session.model.SessionScreenEvent.ToggleLeaveDialog
import xyz.luko.learning.session.model.SessionState
import xyz.luko.learning.session.ui.component.FooterAction
import xyz.luko.learning.session.ui.component.LeaveSessionDialog
import xyz.luko.learning.session.ui.component.Pager
import xyz.luko.learning.session.ui.component.PinyinCharacter
import xyz.luko.learning.session.ui.component.SessionHeader
import xyz.luko.ui.core.TestTags
import xyz.luko.ui.core.preview.PreviewProvider
import xyz.luko.ui.core.window.rememberWindowInfo
import xyz.luko.ui.designsystem.components.page.ErrorScreen
import xyz.luko.ui.designsystem.components.page.LoadingScreen
import xyz.luko.ui.designsystem.components.page.NestedScaffold
import xyz.luko.ui.designsystem.preview.LoadingMode
import xyz.luko.ui.designsystem.preview.LoadingModeProvider
import xyz.luko.ui.designsystem.preview.PreviewScreen
import xyz.luko.ui.designsystem.preview.ThemeMode
import xyz.luko.ui.designsystem.theme.AppTheme
import xyz.luko.ui.designsystem.token.dimens.Padding
import xyz.luko.ui.designsystem.token.dimens.Spacing
import xyz.luko.ui.navigation.AppNavigation
import kotlin.time.Clock

@Composable
internal fun SessionScreen(route: LearningInternalRoute.SessionRoute) {
    val viewModel = koinViewModel<SessionViewModel> {
        parametersOf(route)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    SessionScreen(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun SessionScreen(
    state: SessionState,
    onEvent: (SessionScreenEvent) -> Unit = {},
) {
    when (state) {
        SessionState.Error -> {
            ErrorScreen { onEvent(Reload) }
        }

        SessionState.Loading -> {
            LoadingScreen()
        }

        is SessionState.Success -> {
            SessionScreen(
                state = state,
                onEvent = onEvent,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreen(
    state: SessionState.Success,
    onEvent: (SessionScreenEvent) -> Unit = {},
) {
    val windowInfo = rememberWindowInfo()

    val pagerState =
        rememberPagerState(
            initialPage = state.currentPageIndex,
            pageCount = { state.drawingPageState.size },
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

    LaunchedEffect(state.currentPageIndex) {
        if (pagerState.currentPage != state.currentPageIndex) {
            pagerState.animateScrollToPage(state.currentPageIndex)
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

    var showDialog by remember { mutableStateOf(state.showDebugMenu) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Autofill") },
            text = { Text("Autofill and finish this session") },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(SessionScreenEvent.AutofillDebug)
                    showDialog = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    NestedScaffold(
        topBar = {
            SessionHeader(
                pager = pagerState,
                modifier = Modifier
                    .padding(top = Padding.medium)
                    .statusBarsPadding(),
            )
        },
        content = { paddingValues ->
            Column(
                modifier =
                    Modifier
                        .testTag(TestTags.Screen.SESSION)
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(Padding.large),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                if (windowInfo.isHeightCompact()) {
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
                                pinyin = state.pinyin,
                                modifier = Modifier.wrapContentHeight(),
                            )
                            FooterAction(
                                isLastQuestion = state.isLastQuestion,
                                isAnswered = state.isPageCompleted,
                                onEvent = onEvent,
                            )
                        }

                        Pager(
                            pagerState = pagerState,
                            state = state,
                            modifier = Modifier.weight(1f),
                            onEvent = onEvent
                        )
                    }
                } else {
                    Column(
                        verticalArrangement = Spacing.large,
                    ) {
                        PinyinCharacter(
                            pinyin = state.pinyin,
                            modifier = Modifier.wrapContentHeight(),
                        )
                        Pager(
                            pagerState = pagerState,
                            state = state,
                            modifier = Modifier.weight(1f),
                            onEvent = onEvent
                        )
                        FooterAction(
                            isLastQuestion = state.isLastQuestion,
                            isAnswered = state.isPageCompleted,
                            onEvent = onEvent,
                        )
                    }
                }
            }
        },
    )
}

private fun previewSuccessState(): SessionState.Success {
    val data = PreviewProvider.dictionary

    return SessionState.Success(
        startTime = Clock.System.now(),
        currentPageIndex = 0,
        questions = listOf(data),
        drawingPageState = mapOf(
            data.code to DrawingPageState(
                totalStrokes = data.medians.size,
                referenceStrokes = data.medians
            )
        ),
        showLeaveDialog = false,
    )
}

@Preview
@Composable
private fun PreviewSessionScreen(
    @PreviewParameter(LoadingModeProvider::class) mode: LoadingMode,
) {
    AppTheme(ThemeMode.Day) {
        SessionScreen(
            state =
                when (mode) {
                    LoadingMode.ERROR -> SessionState.Error
                    LoadingMode.SUCCESS -> previewSuccessState()
                    LoadingMode.LOADING -> SessionState.Loading
                },
        )
    }
}

@PreviewScreen
@Composable
private fun PreviewSessionScreenDevices() {
    AppTheme(ThemeMode.Night) {
        SessionScreen(
            state = previewSuccessState()
        )
    }
}
