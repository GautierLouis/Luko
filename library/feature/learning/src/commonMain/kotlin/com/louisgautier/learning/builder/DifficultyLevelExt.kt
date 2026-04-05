package com.louisgautier.learning.builder

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
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
    EASY -> Icons.Filled.Lightbulb
    MEDIUM -> Icons.Filled.Visibility
    HARD -> Icons.Filled.VisibilityOff
}

@Composable
internal fun DifficultyLevel.colorFamily() = when (this) {
    EASY -> Theme.appLevelColors.easy
    MEDIUM -> Theme.appLevelColors.medium
    HARD -> Theme.appLevelColors.hard
}