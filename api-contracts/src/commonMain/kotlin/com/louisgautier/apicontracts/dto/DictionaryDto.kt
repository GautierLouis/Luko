package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryDto(
    val code: Int,
    val definition: String = "",
    val pinyin: List<String> = emptyList(),
    val decomposition: String = "",
    val decompositionList: List<DecompositionDto> = emptyList(),
    val level: CharacterFrequencyLevelDto = CharacterFrequencyLevelDto.UNKNOWN,
    val etymology: EtymologyDto? = null,
    val radical: String? = null,
    val matches: List<List<Int>?> = emptyList()
)