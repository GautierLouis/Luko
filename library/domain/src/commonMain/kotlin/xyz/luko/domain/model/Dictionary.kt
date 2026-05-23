package xyz.luko.domain.model

data class Dictionary(
    val code: Int,
    val pinyin: List<String> = emptyList(),
    val decomposition: List<Decomposition> = emptyList(),
    val level: CharacterFrequencyLevel = CharacterFrequencyLevel.UNKNOWN,
    val strokes: List<String>,
    val medians: List<Stroke>,
)
