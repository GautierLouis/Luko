package xyz.luko.learning.builder

import androidx.compose.runtime.Composable
import xyz.luko.designsystem.icon.AppIcon
import xyz.luko.designsystem.icon.Lightbulb
import xyz.luko.designsystem.icon.Visibility
import xyz.luko.designsystem.icon.VisibilityOff
import xyz.luko.designsystem.theme.Theme
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.DifficultyLevel.EASY
import xyz.luko.domain.model.DifficultyLevel.HARD
import xyz.luko.domain.model.DifficultyLevel.MEDIUM

@Composable
internal fun DifficultyLevel.label() = when (this) {
    EASY -> Theme.strings.easy
    MEDIUM -> Theme.strings.medium
    HARD -> Theme.strings.hard
}

@Composable
internal fun DifficultyLevel.title() = when (this) {
    EASY -> Theme.strings.easyTitle
    MEDIUM -> Theme.strings.mediumTitle
    HARD -> Theme.strings.hardTitle
}

@Composable
internal fun DifficultyLevel.caption() = when (this) {
    EASY -> Theme.strings.easyCaption
    MEDIUM -> Theme.strings.mediumCaption
    HARD -> Theme.strings.hardCaption
}

@Composable
internal fun DifficultyLevel.icon() = when (this) {
    EASY -> AppIcon.Lightbulb
    MEDIUM -> AppIcon.Visibility
    HARD -> AppIcon.VisibilityOff
}

@Composable
internal fun DifficultyLevel.colorFamily() = when (this) {
    EASY -> Theme.appLevelColors.easy
    MEDIUM -> Theme.appLevelColors.medium
    HARD -> Theme.appLevelColors.hard
}