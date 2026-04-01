package com.louisgautier.designsystem.components.metrics.attrs

import androidx.compose.runtime.Composable
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedAvgTime
import com.louisgautier.designsystem.icon.RoundedBolt
import com.louisgautier.designsystem.icon.RoundedTarget
import com.louisgautier.designsystem.theme.Theme

enum class SessionStatistic {
    QuestionCount,
    Time,
    Score,
    Difficulty;

    companion object Companion {

        @Composable
        fun SessionStatistic.label() = when (this) {
            QuestionCount -> Theme.strings.questions
            Time -> Theme.strings.totalScore
            Score -> Theme.strings.sessions
            Difficulty -> Theme.strings.difficulty
        }

        @Composable
        fun SessionStatistic.icon() = when (this) {
            QuestionCount -> AppIcon.RoundedTarget
            Time -> AppIcon.RoundedAvgTime
            Score -> AppIcon.RoundedBolt
            Difficulty -> AppIcon.RoundedBolt
        }
    }
}