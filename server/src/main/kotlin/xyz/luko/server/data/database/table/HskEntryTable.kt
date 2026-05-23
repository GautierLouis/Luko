package xyz.luko.server.data.database.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object HskEntryTable : IntIdTable("hsk_entry") {
    val simplified = varchar("simplified", 32)
    val radical = varchar("radical", 8).nullable()
    val frequency = integer("frequency").nullable()

    // level and pos are List<String> -> stored as comma-separated or child tables
    // keeping it simple: store as text
    val levels = text("levels")        // e.g. "new-3,old-4"
    val pos = text("pos").nullable()   // e.g. "n,v"
}

object HskFormTable : IntIdTable("hsk_form") {
    val entryId = reference("entry_id", HskEntryTable, onDelete = ReferenceOption.CASCADE)
    val traditional = varchar("traditional", 32).nullable()
    val pinyinTranscription = varchar("pinyin", 64).nullable()
    val numericTranscription = varchar("numeric", 64).nullable()
    val wadegiles = varchar("wadegiles", 64).nullable()
    val bopomofo = varchar("bopomofo", 64).nullable()
    val romatzyh = varchar("romatzyh", 64).nullable()
    val meanings = text("meanings").nullable()       // JSON array or newline-separated
    val classifiers = varchar("classifiers", 64).nullable() // usually 0-1 items
}

object HskEntryCharacterTable : IntIdTable("hsk_entry_character") {
    val entryId = reference("entry_id", HskEntryTable, onDelete = ReferenceOption.CASCADE)
    val codePoint = integer("code_point")
}

object HskEntryLevelTable : IntIdTable("hsk_entry_level") {
    val entryId = reference("entry_id", HskEntryTable, onDelete = ReferenceOption.CASCADE)
    val level = varchar("level", 16)
}
