package com.louisgautier.learning.builder

import androidx.compose.runtime.Composable
import com.louisgautier.designsystem.icon.AppIcon
import com.louisgautier.designsystem.icon.Lightbulb
import com.louisgautier.designsystem.icon.Visibility
import com.louisgautier.designsystem.icon.VisibilityOff
import com.louisgautier.designsystem.theme.Theme
import com.louisgautier.domain.model.DifficultyLevel
import com.louisgautier.domain.model.DifficultyLevel.EASY
import com.louisgautier.domain.model.DifficultyLevel.HARD
import com.louisgautier.domain.model.DifficultyLevel.MEDIUM

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