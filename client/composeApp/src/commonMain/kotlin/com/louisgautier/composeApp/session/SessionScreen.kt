package com.louisgautier.composeApp.session

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
import com.louisgautier.composeApp.AppNavigation
import com.louisgautier.composeApp.design.atom.AppButton
import com.louisgautier.composeApp.design.page.ErrorPage
import com.louisgautier.composeApp.design.previewDictionaryWithGraphic
import com.louisgautier.domain.model.Response
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
    } else if (state.questions.isNotEmpty()) {
        SessionScreenContent(
            state = state,
            onComplete = { viewModel.onComplete(it) },
            onFinish = { viewModel.endSession() }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SessionScreenContent(
    state: SessionViewModel.UIState,
    onComplete: (Response) -> Unit,
    onFinish: () -> Unit,
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
                char = state.currentQuestion.dictionary,
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
                ) {
                    GraphicPager(
                        difficulty = state.difficulty,
                        graphic = state.questions[it].graphics,
                        modifier = Modifier.fillMaxWidth()
                    ) { response -> onComplete(response) }
                }

                Spacer(Modifier.weight(3f))

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    AppButton(
                        onClick = {
                            if (state.isLastQuestion) onNext() else onFinish()
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
            questions = listOf(
                previewDictionaryWithGraphic,
            ),
            pagerState = PagerState { 1 }
        ),
        onComplete = { },
        onFinish = {}
    )
}


