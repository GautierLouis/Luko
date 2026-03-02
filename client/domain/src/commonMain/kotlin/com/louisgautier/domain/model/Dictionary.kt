package com.louisgautier.domain.model


data class Dictionary(
    val code: Int,
    val definition: String = "",
    val pinyin: List<String> = emptyList(),
    val decomposition: String = "",
    val decompositionList: List<Decomposition> = emptyList(),
    val level: CharacterFrequencyLevel = CharacterFrequencyLevel.UNKNOWN,
    val etymology: Etymology? = null,
    val radical: String? = null,
    val matches: List<List<Int>?> = emptyList()
) {
    val character: Char
        get() = Char(code)
}