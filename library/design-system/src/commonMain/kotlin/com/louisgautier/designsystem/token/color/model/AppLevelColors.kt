package com.louisgautier.designsystem.token.color.model

import androidx.compose.runtime.Immutable

@Immutable
data class AppLevelColors(
    val common: LevelColors,
    val frequent: LevelColors,
    val standard: LevelColors,
    val easy: LevelColors,
    val medium: LevelColors,
    val hard: LevelColors,
    val streak: LevelColors,
    val sessions: LevelColors,
    val totalScore: LevelColors,
    val appMetrics: LevelColors,
)
