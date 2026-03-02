package com.louisgautier.learning.builder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louisgautier.designsystem.AppTitle
import com.louisgautier.designsystem.ai.Green50
import com.louisgautier.designsystem.components.button.AppButton
import com.louisgautier.domain.model.CharacterFrequencyLevel
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.learning.DifficultyAndQuantityContent
import com.louisgautier.utils.AppNavigation
import com.louisgautier.utils.Route
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SessionBuilderScreen() {
    val scope = rememberCoroutineScope()

    val selectedLevels = rememberSaveable { mutableStateSetOf(CharacterFrequencyLevel.COMMON) }
    var selectedDifficulty by rememberSaveable { mutableStateOf(Difficulty.EASY) }
    var questionCount by rememberSaveable { mutableStateOf(QuestionCount.FIVE) }

    val pager = rememberPagerState(initialPage = 0) { 2 }

    fun btnClick() {
        if (pager.canScrollForward) {
            scope.launch { pager.animateScrollToPage(pager.currentPage + 1) }
        } else {
            AppNavigation.navigate(
                Route.SessionRoute(
                    levels = selectedLevels.joinToString(),
                    difficulty = selectedDifficulty.toString(),
                    limit = questionCount.toString()
                ),
                true
            )
        }
    }

    Scaffold(
        topBar = { AppTitle("New Session") },
        containerColor = Green50
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            HorizontalPager(
                state = pager,
                userScrollEnabled = false,
                pageSpacing = 16.dp,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) { page ->
                when (page) {
                    0 -> CharacterGroupPicker(
                        selectedLevels = selectedLevels,
                        onClick = { level ->
                            if (level !in selectedLevels) {
                                selectedLevels.add(level)
                            } else {
                                selectedLevels.remove(level)
                            }
                        }
                    )

                    1 -> DifficultyAndQuantityContent(
                        selectedDifficulty = selectedDifficulty,
                        questionCount = questionCount,
                        onDifficultySelected = { selectedDifficulty = it },
                        onQuestionCountSelected = { questionCount = it }
                    )
                }
            }

            AppButton(
                onClick = { btnClick() },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = if (pager.canScrollForward) "Next" else "Start",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
@Preview
fun QuizBuilderScreenPreview() {
    MaterialTheme {
        SessionBuilderScreen()
    }
}