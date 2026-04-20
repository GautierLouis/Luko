package com.louisgautier.server.domain.repo.implem

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import com.louisgautier.apicontracts.dto.DictionaryDto
import com.louisgautier.apicontracts.dto.LevelCountDto
import com.louisgautier.apicontracts.dto.ResponseListDto
import com.louisgautier.apicontracts.dto.SimpleDictionaryDto
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
import com.louisgautier.server.domain.mapper.toDictionary
import com.louisgautier.server.domain.mapper.toDictionaryWithGraphic
import com.louisgautier.server.domain.mapper.toSimpleDictionary
import com.louisgautier.server.domain.repo.DictionaryRepository
import com.louisgautier.server.domain.suspendTransaction
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import kotlin.random.Random

class DefaultDictionaryRepository : DictionaryRepository {

    private fun SqlExpressionBuilder.isValid() =
        ((pinyin.isNotNull() or decompositionList.neq("[]")) and (level inList CharacterFrequencyLevelDto.validEntry))

    override suspend fun getLevelCount() = suspendTransaction {
        DictionaryTable
            .select(level, code.count())
            .groupBy(level)
            .map { row ->
                LevelCountDto(row[level], row[code.count()].toInt())
            }
    }

    override suspend fun getRandomCharacters(levels: List<CharacterFrequencyLevelDto>, limit: Int) =
        suspendTransaction {
            val total = DictionaryTable
                .join(GraphicTable, JoinType.INNER, code, GraphicTable.code)
                .selectAll()
                .where { (level inList levels) and isValid() }
                .count()

            val offset = if (total > limit) Random.nextLong(0, total - limit) else 0L

            DictionaryTable
                .join(GraphicTable, JoinType.INNER, code, GraphicTable.code)
                .selectAll()
                .where { (level inList levels) and isValid() }
                .limit(limit)
                .offset(offset)
                .map {
                    it.toDictionaryWithGraphic()
                }

        }

    override suspend fun getAll(
        page: Int,
        limit: Int,
        levels: List<CharacterFrequencyLevelDto>
    ): ResponseListDto<DictionaryDto> = suspendTransaction {
        val result = DictionaryTable.selectAll()
            .where { (level inList levels) and isValid() }
            .limit(limit + 1)
            .offset(page * limit.toLong())
            .toList()

        val data = result.dropLast(1).map { it.toDictionary() }
        val hasNextPage = result.size > limit
        ResponseListDto(hasNextPage, data)
    }

    override suspend fun getByLevel(
        page: Int,
        limit: Int,
        level: CharacterFrequencyLevelDto
    ): ResponseListDto<SimpleDictionaryDto> = suspendTransaction {
        val result = DictionaryTable.selectAll()
            .where { (DictionaryTable.level eq level) and isValid() }
            .limit(limit + 1)
            .offset(page * limit.toLong())
            .toList()

        val data = result.dropLast(1).map { it.toSimpleDictionary() }
        val hasNextPage = result.size > limit
        ResponseListDto(hasNextPage, data)
    }

    override suspend fun get(code: Int): DictionaryDto? = suspendTransaction {
        DictionaryTable.selectAll().where { DictionaryTable.code eq code }.limit(1)
            .map { it.toDictionary() }.firstOrNull()
    }

    override suspend fun batchCreate(dictionary: List<DictionaryDto>) = suspendTransaction {
        DictionaryTable.batchInsert(
            data = dictionary,
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
        return@suspendTransaction
    }
}