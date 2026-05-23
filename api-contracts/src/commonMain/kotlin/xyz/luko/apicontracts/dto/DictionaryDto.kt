package xyz.luko.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryDto(
    val code: Int,
    val pinyin: List<String> = emptyList(),
    val decomposition: List<DecompositionDto> = emptyList(),
    val level: CharacterFrequencyLevelDto = CharacterFrequencyLevelDto.UNKNOWN,
    val strokes: List<String>,
    val medians: List<StrokeDto>
)
