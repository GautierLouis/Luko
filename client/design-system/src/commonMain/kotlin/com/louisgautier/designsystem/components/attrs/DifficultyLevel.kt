package com.louisgautier.designsystem.components.attrs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import com.louisgautier.designsystem.theme.Theme

enum class DifficultyLevel {
    EASY,
    MEDIUM,
    HARD;

    companion object {
        @Composable
        fun DifficultyLevel.label() = when (this) {
            EASY -> Theme.strings.easy
            MEDIUM -> Theme.strings.medium
            HARD -> Theme.strings.hard
        }

        @Composable
        fun DifficultyLevel.title() = when (this) {
            EASY -> Theme.strings.easyTitle
            MEDIUM -> Theme.strings.mediumTitle
            HARD -> Theme.strings.hardTitle
        }

        @Composable
        fun DifficultyLevel.caption() = when (this) {
            EASY -> Theme.strings.easyCaption
            MEDIUM -> Theme.strings.mediumCaption
            HARD -> Theme.strings.hardCaption
        }

        @Composable
        fun DifficultyLevel.icon() = when (this) {
            EASY -> Icons.Filled.Lightbulb
            MEDIUM -> Icons.Filled.Visibility
            HARD -> Icons.Filled.VisibilityOff
        }

        @Composable
        fun DifficultyLevel.colorFamily() = when (this) {
            EASY -> Theme.appLevelColors.easy
            MEDIUM -> Theme.appLevelColors.medium
            HARD -> Theme.appLevelColors.hard
        }
    }
}
