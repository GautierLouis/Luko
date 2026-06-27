package xyz.luko.server.data.database.table

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object CharacterTable : IntIdTable("character") {
    val code = integer("code").uniqueIndex()
    val definition = text("definition").nullable()
    val pinyin = text("pinyin").nullable()
    val decomposition = varchar("decomposition", 110)
    val etymologyType = varchar("etymology_type", 50).nullable()
    val etymologyPhonetic = text("etymology_phonetic").nullable()
    val etymologySemantic = text("etymology_semantic").nullable()
    val etymologyHint = text("etymology_hint").nullable()
    val radical = varchar("radical", 4).nullable()
    val matches = text("matches").nullable()
}

