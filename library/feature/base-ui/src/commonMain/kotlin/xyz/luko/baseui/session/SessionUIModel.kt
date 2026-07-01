package xyz.luko.baseui.session

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Session
import xyz.luko.ui.designsystem.components.metrics.SessionUiModel
import xyz.luko.ui.designsystem.theme.Theme
import xyz.luko.utils.toAccessibilityDate
import xyz.luko.utils.toFormattedString
import xyz.luko.utils.toHHMMSS
import xyz.luko.utils.toISODateString
import xyz.luko.utils.toPercentage

@Composable
fun Session.toUiModel(): SessionUiModel {
    val pattern = Theme.strings.datePattern
    return SessionUiModel(
        date = remember(date) { date.toISODateString(pattern) },
        accessibilityDate = remember(date) { date.toAccessibilityDate() },
        duration = remember(duration) { duration.toHHMMSS() },
        difficulty =
            when (difficulty) {
                DifficultyLevel.EASY -> Theme.strings.easy
                DifficultyLevel.MEDIUM -> Theme.strings.medium
                DifficultyLevel.HARD -> Theme.strings.hard
            },
        questionsCount = questionsCount.toString(),
        score = score.toFormattedString(),
        accuracy = accuracy.toPercentage()
    )
}

