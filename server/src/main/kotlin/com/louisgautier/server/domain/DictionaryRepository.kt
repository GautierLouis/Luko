package com.louisgautier.server.domain

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevel
import com.louisgautier.apicontracts.dto.Dictionary
import com.louisgautier.apicontracts.dto.LevelCount
import com.louisgautier.apicontracts.dto.ResponseList
import com.louisgautier.apicontracts.dto.SimpleDictionary
import com.louisgautier.server.database.entity.DictionaryTable
import com.louisgautier.server.database.entity.DictionaryTable.code
import com.louisgautier.server.database.entity.DictionaryTable.decomposition
import com.louisgautier.server.database.entity.DictionaryTable.decompositionList
import com.louisgautier.server.database.entity.DictionaryTable.definition
import com.louisgautier.server.database.entity.DictionaryTable.etymologyHint
import com.louisgautier.server.database.entity.DictionaryTable.etymologyPhonetic
import com.louisgautier.server.database.entity.DictionaryTable.etymologySemantic
import com.louisgautier.server.database.entity.DictionaryTable.etymologyType
import com.louisgautier.server.database.entity.DictionaryTable.level
import com.louisgautier.server.database.entity.DictionaryTable.matches
import com.louisgautier.server.database.entity.DictionaryTable.pinyin
import com.louisgautier.server.database.entity.DictionaryTable.radical
import com.louisgautier.server.database.entity.GraphicTable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Random
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class DictionaryRepository {

    private fun SqlExpressionBuilder.isValid() =
        ((pinyin.isNotNull() or decompositionList.neq("[]")) and (level inList CharacterFrequencyLevel.validEntry))

    suspend fun getLevelCount() = suspendTransaction {
        DictionaryTable
            .select(level, code.count())
            .groupBy(level)
            .map { row ->
                LevelCount(row[level], row[code.count()].toInt())
            }
    }

    suspend fun getRandomCharacters(levels: List<CharacterFrequencyLevel>, limit: Int) =
        suspendTransaction {
            val total = DictionaryTable
                .join(GraphicTable, JoinType.INNER, DictionaryTable.code, GraphicTable.code)
                .selectAll()
                .where { (level inList levels) and isValid() }
                .count()

            val offset = kotlin.random.Random.nextLong(0, maxOf(0, total - limit))

            DictionaryTable
                .join(GraphicTable, JoinType.INNER, DictionaryTable.code, GraphicTable.code)
                .selectAll()
                .where { (level inList levels) and isValid() }
                .limit(limit)
                .offset(offset)
                .map {
                    it.toDictionaryWithGraphic()
                }

        }

    suspend fun getAll(
        page: Int,
        limit: Int,
        levels: List<CharacterFrequencyLevel>
    ): ResponseList<Dictionary> = suspendTransaction {
        val result = DictionaryTable.selectAll()
            .where { (level inList levels) and isValid() }
            .limit(limit + 1)
            .offset(page * limit.toLong())
            .toList()

        val data = result.dropLast(1).map { it.toDictionary() }
        val hasNextPage = result.size > limit
        ResponseList(hasNextPage, data)
    }

//    suspend fun updateDef() = suspendTransaction {
//        DictionaryTable.select(code, decompositionList)
//            .where { decompositionList.neq("[]") and code.neq(21241) }
//            .toList()
//            .map { row ->
//                val json = row[decompositionList].orEmpty()
//                val list = Json.decodeFromString<List<DecompositionOld>>(json)
//                val new = list.map { old -> Decomposition(old.symbol.code, old.glyphs.map { it.code }) }
//                DictionaryTable.update( { code eq row[code] }) {
//                    it[decompositionList] = Json.encodeToString(new)
//                }
//            }
//    }

    suspend fun getByLevel(
        page: Int,
        limit: Int,
        level: CharacterFrequencyLevel
    ): ResponseList<SimpleDictionary> = suspendTransaction {
        val result = DictionaryTable.selectAll()
            .where { (DictionaryTable.level eq level) and isValid() }
            .limit(limit + 1)
            .offset(page * limit.toLong())
            .toList()

        val data = result.dropLast(1).map { it.toSimpleDictionary() }
        val hasNextPage = result.size > limit
        ResponseList(hasNextPage, data)
    }

    suspend fun get(code: Int): Dictionary? = suspendTransaction {
        DictionaryTable.selectAll().where { DictionaryTable.code eq code }.limit(1)
            .map { it.toDictionary() }.firstOrNull()
    }

    suspend fun batchCreate(dictionary: List<Dictionary>) = suspendTransaction {
        DictionaryTable.batchInsert(
            dictionary,
            ignore = true,
            shouldReturnGeneratedValues = false
        ) { dictionary ->
            this[code] = dictionary.code
            this[definition] = dictionary.definition
            this[pinyin] = dictionary.pinyin.joinToString()
            this[decomposition] = dictionary.decomposition
            this[decompositionList] = Json.encodeToString(dictionary.decompositionList)
            this[level] = dictionary.level
            this[etymologyType] = dictionary.etymology?.type
            this[etymologyPhonetic] = dictionary.etymology?.phonetic
            this[etymologySemantic] = dictionary.etymology?.semantic
            this[etymologyHint] = dictionary.etymology?.hint
            this[radical] = dictionary.radical
            this[matches] = Json.encodeToString(dictionary.matches)
        }
    }
}