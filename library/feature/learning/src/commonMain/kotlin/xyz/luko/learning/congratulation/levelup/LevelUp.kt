package xyz.luko.learning.congratulation.levelup

import xyz.luko.domain.model.Level

internal data class LevelUp(
    val previousLevel: Level,
    val currentLevel: Level,
    val characters: List<String>
)
