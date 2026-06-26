package xyz.luko.baseui.session

import androidx.compose.runtime.Composable
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.DifficultyLevel.EASY
import xyz.luko.domain.model.DifficultyLevel.HARD
import xyz.luko.domain.model.DifficultyLevel.MEDIUM
import xyz.luko.ui.designsystem.icon.AppIcon
import xyz.luko.ui.designsystem.icon.Lightbulb
import xyz.luko.ui.designsystem.icon.Visibility
import xyz.luko.ui.designsystem.icon.VisibilityOff
import xyz.luko.ui.designsystem.theme.Theme

@Composable
fun DifficultyLevel.label() =
    when (this) {
        EASY -> Theme.strings.easy
        MEDIUM -> Theme.strings.medium
        HARD -> Theme.strings.hard
    }

@Composable
fun DifficultyLevel.title() =
    when (this) {
        EASY -> Theme.strings.easyTitle
        MEDIUM -> Theme.strings.mediumTitle
        HARD -> Theme.strings.hardTitle
    }

@Composable
fun DifficultyLevel.caption() =
    when (this) {
        EASY -> Theme.strings.easyCaption
        MEDIUM -> Theme.strings.mediumCaption
        HARD -> Theme.strings.hardCaption
    }

@Composable
fun DifficultyLevel.icon() =
    when (this) {
        EASY -> AppIcon.Lightbulb
        MEDIUM -> AppIcon.Visibility
        HARD -> AppIcon.VisibilityOff
    }

@Composable
fun DifficultyLevel.colorFamily() =
    when (this) {
        EASY -> Theme.appLevelColors.easy
        MEDIUM -> Theme.appLevelColors.medium
        HARD -> Theme.appLevelColors.hard
    }
