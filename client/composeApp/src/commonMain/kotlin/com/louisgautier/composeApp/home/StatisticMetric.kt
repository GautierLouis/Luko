package com.louisgautier.composeApp.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.louisgautier.designsystem.ai.Orange100
import com.louisgautier.designsystem.ai.Orange700
import com.louisgautier.designsystem.ai.Purple100
import com.louisgautier.designsystem.ai.Purple700
import com.louisgautier.designsystem.ai.Teal100
import com.louisgautier.designsystem.ai.Teal700
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedBolt
import com.louisgautier.designsystem.icon.RoundedCalendarToday
import com.louisgautier.designsystem.icon.RoundedStreak
import com.louisgautier.utils.CoreKeepForR8

@CoreKeepForR8
enum class StatisticMetric(
    val title: String,
    val icon: ImageVector,
    val containerColor: Color,
    val contentColor: Color,
) {

    Streak(
        title = "Day Streak",
        icon = AppIcon.RoundedStreak,
        containerColor = Orange100,
        contentColor = Orange700,
    ),

    Sessions(
        title = "Sessions",
        icon = AppIcon.RoundedCalendarToday,
        containerColor = Teal100,
        contentColor = Teal700,
    ),

    TotalScore(
        title = "Total XP",
        icon = AppIcon.RoundedBolt,
        containerColor = Purple100,
        contentColor = Purple700,
    ),
}

