package xyz.luko.server.data.database

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import xyz.luko.server.data.database.table.CharacterTable
import xyz.luko.server.data.database.table.DictionaryTable
import xyz.luko.server.data.database.table.GraphicTable
import xyz.luko.server.data.database.table.HanziTable
import xyz.luko.server.data.database.table.HskEntryCharacterTable
import xyz.luko.server.data.database.table.HskEntryLevelTable
import xyz.luko.server.data.database.table.HskEntryTable
import xyz.luko.server.data.database.table.HskFormTable
import xyz.luko.server.domain.model.CharacterRow
import xyz.luko.server.domain.model.DictionaryRow
import xyz.luko.server.domain.model.GraphicRow
import xyz.luko.server.domain.model.HanziRow
import xyz.luko.server.domain.model.HskEntryRow
import xyz.luko.server.domain.model.HskFormRow

internal object BatchInsertMapping {

    fun BatchInsertStatement.add(entity: GraphicRow) {
        this[GraphicTable.code] = entity.code
        this[GraphicTable.strokes] = entity.strokes
        this[GraphicTable.medians] = entity.medians
    }

    fun BatchInsertStatement.add(entity: CharacterRow) {
        this[CharacterTable.code] = entity.code
        this[CharacterTable.definition] = entity.definition
        this[CharacterTable.pinyin] = entity.pinyin
        this[CharacterTable.decomposition] = entity.decomposition
        this[CharacterTable.etymologyType] = entity.etymologyType
        this[CharacterTable.etymologyPhonetic] = entity.etymologyPhonetic
        this[CharacterTable.etymologySemantic] = entity.etymologySemantic
        this[CharacterTable.etymologyHint] = entity.etymologyHint
        this[CharacterTable.radical] = entity.radical
        this[CharacterTable.matches] = entity.matches
    }

    fun BatchInsertStatement.add(entity: HanziRow) {
        this[HanziTable.id] = entity.thisTable
        this[HanziTable.code] = entity.code
        this[HanziTable.traditional] = entity.traditional
        this[HanziTable.pinyin] = entity.pinyin
        this[HanziTable.pinyinStyle2] = entity.pinyinStyle2
        this[HanziTable.zhuyinBopomofo] = entity.zhuyinBopomofo
        this[HanziTable.jyupting] = entity.jyupting
        this[HanziTable.decomposition1] = entity.decomposition1
        this[HanziTable.decomposition2WithRadical] = entity.decomposition2WithRadical
        this[HanziTable.meaningDecomposition2WithRadical] =
            entity.meaningDecomposition2WithRadical
        this[HanziTable.decomposition3Graphical] = entity.decomposition3Graphical
        this[HanziTable.componentIn] = entity.componentIn
        this[HanziTable.exampleWords] = entity.exampleWords
        this[HanziTable.meaningJunda] = entity.meaningJunda
        this[HanziTable.keyWordRsh] = entity.keyWordRsh
        this[HanziTable.hsk30Level] = entity.hsk30Level
        this[HanziTable.rankRsh] = entity.rankRsh
        this[HanziTable.frequencyJunda] = entity.frequencyJunda
        this[HanziTable.indexGscc] = entity.indexGscc
        this[HanziTable.learningOrderCcm] = entity.learningOrderCcm
        this[HanziTable.ccCedictDefinitions] = entity.ccCedictDefinitions
    }

    fun BatchInsertStatement.add(entity: HskEntryRow) {
        this[HskEntryTable.simplified] = entity.simplified
        this[HskEntryTable.radical] = entity.radical
        this[HskEntryTable.frequency] = entity.frequency
        this[HskEntryTable.levels] = entity.level
        this[HskEntryTable.pos] = entity.pos
    }

    fun BatchInsertStatement.add(
        entryId: Int,
        entity: HskFormRow
    ) {
        this[HskFormTable.entryId] = EntityID(entryId, HskEntryTable)
        this[HskFormTable.traditional] = entity.traditional
        this[HskFormTable.pinyinTranscription] = entity.transcriptions?.pinyin
        this[HskFormTable.numericTranscription] = entity.transcriptions?.numeric
        this[HskFormTable.wadegiles] = entity.transcriptions?.wadegiles
        this[HskFormTable.bopomofo] = entity.transcriptions?.bopomofo
        this[HskFormTable.romatzyh] = entity.transcriptions?.romatzyh
        this[HskFormTable.meanings] = entity.meanings
        this[HskFormTable.classifiers] = entity.classifiers
    }

    fun BatchInsertStatement.add(
        entryId: Int,
        codePoint: Int,
    ) {
        this[HskEntryCharacterTable.entryId] = EntityID(entryId, HskEntryTable)
        this[HskEntryCharacterTable.codePoint] = codePoint
    }

    fun BatchInsertStatement.add(
        entryId: Int,
        level: String,
    ) {
        this[HskEntryLevelTable.entryId] = EntityID(entryId, HskEntryTable)
        this[HskEntryLevelTable.level] = level
    }

    fun BatchInsertStatement.add(entity: DictionaryRow) {
        this[DictionaryTable.code] = entity.code
        this[DictionaryTable.char] = entity.char
        this[DictionaryTable.decomposition] = entity.decomposition
        this[DictionaryTable.medians] = entity.medians
        this[DictionaryTable.level] = entity.frequency
        this[DictionaryTable.hskLevel] = entity.hskLevel
    }
}
