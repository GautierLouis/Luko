package xyz.luko.server.data.database.table

import org.jetbrains.exposed.dao.id.IntIdTable

object DictionaryTable : IntIdTable("dictionary") {
    val code = integer("code").uniqueIndex()
    val char = varchar("char", 4)
    val decomposition = text("decomposition").nullable()
    val medians = text("medians")
    val level = integer("level")
    val hskLevel = integer("hsk_level")
}
