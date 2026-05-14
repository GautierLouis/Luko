package xyz.luko.server.domain.usecase.parser

import kotlinx.serialization.Serializable
import xyz.luko.apicontracts.dto.EtymologyDto

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