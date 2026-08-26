package xyz.luko.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionSettings(
    val difficultyLevel: DifficultyLevel,
    val count: Int,
    val frequencyLevel: List<CharacterFrequencyLevel>
)
