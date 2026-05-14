package xyz.luko.server.domain.model

data class DictionaryEntity(
    val code: Int,
    val definition: String,
    val pinyin: String,
    val decomposition: String,
    val decompositionList: String,
    val level: CharacterFrequencyLevelEntity,
    val etymologyType: EtymologyTypeEntity?,
    val etymologyPhonetic: String?,
    val etymologySemantic: String?,
    val etymologyHint: String?,
    val radical: String?,
    val matches: String,
)