package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class SimpleDictionaryDto(
    val code: Int,
    val pinyin: List<String> = emptyList(),
    val level: CharacterFrequencyLevelDto = CharacterFrequencyLevelDto.UNKNOWN,
) {
    val character: Char
        get() = Char(code)
}