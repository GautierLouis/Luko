package xyz.luko.ui.designsystem.components.metrics.attrs

import androidx.compose.runtime.Composable
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.RoundedAvgTime
import xyz.luko.ui.designsystem.icon.RoundedBolt
import xyz.luko.ui.designsystem.icon.RoundedCalendarToday
import xyz.luko.ui.designsystem.icon.RoundedCrown
import xyz.luko.ui.designsystem.icon.RoundedStreak
import xyz.luko.ui.designsystem.theme.Theme

enum class AppStatistic {
    Streak,
    Sessions,
    AvgAccuracy,
    AvgDifficulty,
    AvgTime,
    ;

    companion object {
        @Composable
        fun AppStatistic.label() =
            when (this) {
                Streak -> Theme.strings.streak
                Sessions -> Theme.strings.sessions
                AvgAccuracy -> Theme.strings.avgAccuracy
                AvgDifficulty -> Theme.strings.avgDifficulty
                AvgTime -> Theme.strings.avgDifficulty
            }

        @Composable
        fun AppStatistic.icon() =
            when (this) {
                Streak -> AppIcon.RoundedStreak
                Sessions -> AppIcon.RoundedCalendarToday
                AvgAccuracy -> AppIcon.RoundedBolt
                AvgDifficulty -> AppIcon.RoundedCrown
                AvgTime -> AppIcon.RoundedAvgTime
            }

        @Composable
        fun AppStatistic.colors() =
            when (this) {
                Streak -> Theme.appLevelColors.streak
                Sessions -> Theme.appLevelColors.sessions
                AvgAccuracy -> Theme.appLevelColors.avgAccuracy
                AvgDifficulty -> Theme.appLevelColors.medium
                AvgTime -> Theme.appLevelColors.medium
            }
    }
}
