package com.louisgautier.baseui.session

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.louisgautier.designsystem.components.metrics.SessionUiModel
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.domain.model.DifficultyLevel
import com.louisgautier.domain.model.Session
import com.louisgautier.utils.toHHMMSS
import com.louisgautier.utils.toISODateString

@Composable
fun Session.toUiModel(): SessionUiModel =
    SessionUiModel(
        date = remember(date) { date.toISODateString() },
        duration = remember(duration) { duration.toHHMMSS() },
        difficulty =
            when (difficulty) {
                DifficultyLevel.EASY -> Theme.strings.easy
                DifficultyLevel.MEDIUM -> Theme.strings.medium
                DifficultyLevel.HARD -> Theme.strings.hard
            },
        questionsCount = questionsCount.toString(),
        score = score.toString(),
    )
