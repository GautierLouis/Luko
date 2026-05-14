package xyz.luko.domain.mapper

import xyz.luko.domain.model.CharacterFrequencyLevel

data class LevelCount(
    val level: CharacterFrequencyLevel,
    val count: Int,
)
