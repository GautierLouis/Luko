package com.louisgautier.designsystem

import androidx.compose.ui.graphics.vector.ImageVector
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.RoundedAvgTime
import com.louisgautier.designsystem.icon.RoundedBolt
import com.louisgautier.designsystem.icon.RoundedTarget
import com.louisgautier.utils.CoreKeepForR8

@CoreKeepForR8
enum class SessionMetric(
    val title: String,
    val icon: ImageVector,
) {

    QuestionCount(title = "Questions", AppIcon.RoundedTarget),
    Time(title = "Time", icon = AppIcon.RoundedAvgTime),
    Score(title = "Score", icon = AppIcon.RoundedBolt),
    Difficulty(title = "Difficulty", icon = AppIcon.RoundedBolt),
}