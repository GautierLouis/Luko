package xyz.luko.designsystem.components.metrics.attrs

import androidx.compose.runtime.Composable
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.RoundedBolt
import xyz.luko.designsystem.icon.RoundedCalendarToday
import xyz.luko.designsystem.icon.RoundedStreak
import xyz.luko.designsystem.theme.Theme

enum class AppStatistic {
    Streak,
    Sessions,
    TotalScore,
    ;

    companion object {
        @Composable
        fun AppStatistic.label() =
            when (this) {
                Streak -> Theme.strings.streak
                Sessions -> Theme.strings.sessions
                TotalScore -> Theme.strings.totalScore
            }

        @Composable
        fun AppStatistic.icon() =
            when (this) {
                Streak -> AppIcon.RoundedStreak
                Sessions -> AppIcon.RoundedCalendarToday
                TotalScore -> AppIcon.RoundedBolt
            }

        @Composable
        fun AppStatistic.colors() =
            when (this) {
                Streak -> Theme.appLevelColors.streak
                Sessions -> Theme.appLevelColors.sessions
                TotalScore -> Theme.appLevelColors.totalScore
            }
    }
}
