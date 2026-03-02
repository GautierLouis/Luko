package com.louisgautier.domain.model

data class SimpleDictionary(
    val code: Int,
    val pinyin: List<String> = emptyList(),
    val level: CharacterFrequencyLevel = CharacterFrequencyLevel.UNKNOWN,
) {
    val character: Char
        get() = Char(code)
}

