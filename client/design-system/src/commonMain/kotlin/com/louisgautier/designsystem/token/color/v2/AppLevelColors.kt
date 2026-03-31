package com.louisgautier.designsystem.token.color.v2

import androidx.compose.runtime.Immutable

@Immutable
data class AppLevelColors(
    val common: LevelColors,
    val frequent: LevelColors,
    val standard: LevelColors,
    val easy: LevelColors,
    val medium: LevelColors,
    val hard: LevelColors,
)