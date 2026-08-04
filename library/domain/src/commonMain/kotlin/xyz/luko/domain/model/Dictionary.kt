package xyz.luko.domain.model

import xyz.luko.apicontracts.dto.IdeographicNodeDto

data class Dictionary(
    val code: Int,
    val pinyin: List<String> = emptyList(),
    val decomposition: IdeographicNodeDto?,
    val level: CharacterFrequencyLevel = CharacterFrequencyLevel.UNKNOWN,
    val strokes: List<String>,
    val medians: List<Stroke>,
)
