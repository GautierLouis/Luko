package xyz.luko.server.database.entity

import org.jetbrains.exposed.dao.id.IntIdTable
import xyz.luko.server.domain.model.CharacterFrequencyLevelEntity
import xyz.luko.server.domain.model.EtymologyTypeEntity

object DictionaryTable : IntIdTable("dictionary") {
    val code = integer("code").uniqueIndex()
    val definition = text("definition").nullable()
    val pinyin = text("pinyin").nullable()
    val decomposition = varchar("original_decomposition", 110)
    val decompositionList = text("decomposition").nullable()
    val level = enumerationByName("level", 50, CharacterFrequencyLevelEntity::class)
    val etymologyType =
        enumerationByName("etymology_type", 50, EtymologyTypeEntity::class).nullable()
    val etymologyPhonetic = text("etymology_phonetic").nullable()
    val etymologySemantic = text("etymology_semantic").nullable()
    val etymologyHint = text("etymology_hint").nullable()
    val radical = varchar("radical", 4).nullable()
    val matches = text("matches").nullable()
}