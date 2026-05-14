package xyz.luko.baseui.session

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import xyz.luko.designsystem.components.metrics.SessionUiModel
import xyz.luko.designsystem.theme.Theme
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Session
import xyz.luko.utils.toHHMMSS
import xyz.luko.utils.toISODateString

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
