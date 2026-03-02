package com.louisgautier.learning.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.louisgautier.designsystem.components.button.AppButton
import com.louisgautier.designsystem.components.page.ErrorPage
import com.louisgautier.designsystem.components.page.LoadingPageContent
import com.louisgautier.domain.previewDictionaryWithGraphic
import com.louisgautier.utils.AppNavigation
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SessionScreen(
    viewModel: SessionViewModel = koinViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isError) {
        ErrorPage(action = { viewModel.loadQuestions() })
    } else if (state.isLoading) {
        LoadingPageContent()
    } else {
        SessionScreenContent(
            state = state,
            event = { viewModel.onSketcherEvent(it) },
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SessionScreenContent(
    state: SessionViewModel.UIState,
    event: (SessionEvent) -> Unit,
) {

    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        LeaveSessionDialog(
            onDismissRequest = { showDialog = false },
            onConfirmation = { AppNavigation.navigateHome() }
        )
    }

    BackHandler(true) {
        showDialog = true
    }

    fun onNext() {
        scope.launch {
            state.pagerState.animateScrollToPage(state.pagerState.currentPage + 1)
        }
    }

    Scaffold(
        topBar = {
            Header(
                pager = state.pagerState,
                char = state.currentQuestion.question.dictionary,
                modifier = Modifier.padding(top = 32.dp)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(Modifier.weight(1f))
                HorizontalPager(
                    userScrollEnabled = false,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    state = state.pagerState
                ) { page ->

                    GraphicSketcher(
                        questionState = state.currentQuestion,
                        drawReference = state.drawReference,
                        drawHint = state.drawHint,
                        onEvent = { event(it) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.weight(3f))

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    AppButton(
                        onClick = {
                            if (state.isLastQuestion) onNext() else event(SessionEvent.Finish)
                        },
                        enabled = state.isAnswered,
                    ) {
                        Text(
                            text = if (state.isLastQuestion) "Complete" else "Finish",
                            fontSize = 16.sp,
                        )
                    }
                    AppButton(
                        onClick = { showDialog = true },
                        containerColor = Color.Transparent,
                        contentColor = Color.Red,
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Text(
                            text = "Quit",
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun SessionScreenContentPreview() {
    SessionScreenContent(
        state = SessionViewModel.UIState(
            questionStates = listOf(
                SessionViewModel.QuestionState(
                    question = previewDictionaryWithGraphic,
                    sketcherState = SessionViewModel.GraphicSketcherState(),
                )
            ),
            pagerState = PagerState { 1 },
        ),
        event = {}
    )
}


