package xyz.luko.designsystem.components.metrics.attrs

import androidx.compose.runtime.Composable
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.RoundedAvgTime
import xyz.luko.designsystem.icon.RoundedBolt
import xyz.luko.designsystem.icon.RoundedTarget
import xyz.luko.designsystem.theme.Theme

enum class SessionStatistic {
    QuestionCount,
    Time,
    Score,
    Difficulty,
    ;

    companion object Companion {
        @Composable
        fun SessionStatistic.label() =
            when (this) {
                QuestionCount -> Theme.strings.questions
                Time -> Theme.strings.totalScore
                Score -> Theme.strings.sessions
                Difficulty -> Theme.strings.difficulty
            }

        @Composable
        fun SessionStatistic.icon() =
            when (this) {
                QuestionCount -> AppIcon.RoundedTarget
                Time -> AppIcon.RoundedAvgTime
                Score -> AppIcon.RoundedBolt
                Difficulty -> AppIcon.RoundedBolt
            }
    }
}
