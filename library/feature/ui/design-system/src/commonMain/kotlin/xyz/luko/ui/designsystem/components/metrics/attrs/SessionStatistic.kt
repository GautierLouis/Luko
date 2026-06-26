package xyz.luko.ui.designsystem.components.metrics.attrs

import androidx.compose.runtime.Composable
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.RoundedAvgTime
import xyz.luko.ui.designsystem.icon.RoundedBolt
import xyz.luko.ui.designsystem.icon.RoundedTarget
import xyz.luko.ui.designsystem.theme.Theme

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
                Time -> Theme.strings.time
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
