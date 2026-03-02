package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class LevelCountDto(val level: CharacterFrequencyLevelDto, val count: Int)