package com.louisgautier.designsystem.components.metrics.attrs

import androidx.compose.runtime.Composable
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedBolt
import com.louisgautier.designsystem.icon.RoundedCalendarToday
import com.louisgautier.designsystem.icon.RoundedStreak
import com.louisgautier.designsystem.theme.Theme

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
