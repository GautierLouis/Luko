package com.louisgautier.server.parser

import com.louisgautier.apicontracts.dto.EtymologyDto
import kotlinx.serialization.Serializable

@Serializable
data class DictionaryParsed(
    val character: Char,
    val definition: String = "",
    val pinyin: List<String> = emptyList(),
    val decomposition: String = "",
    val etymology: EtymologyDto? = null,
    val radical: String? = null,
    val matches: List<List<Int>?> = emptyList()
)