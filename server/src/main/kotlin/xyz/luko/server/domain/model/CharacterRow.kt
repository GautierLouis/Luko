package xyz.luko.server.domain.model

data class CharacterRow(
    val code: Int,
    val definition: String,
    val pinyin: String,
    val decomposition: String,
    val etymologyType: String?,
    val etymologyPhonetic: String?,
    val etymologySemantic: String?,
    val etymologyHint: String?,
    val radical: String?,
    val matches: String,
)
