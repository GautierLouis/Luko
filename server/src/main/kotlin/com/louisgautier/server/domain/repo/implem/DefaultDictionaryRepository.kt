package com.louisgautier.server.domain.repo.implem

import com.louisgautier.apicontracts.dto.DictionaryDto
import com.louisgautier.apicontracts.dto.DictionaryWithGraphicDto
import com.louisgautier.apicontracts.dto.LevelCountDto
import com.louisgautier.apicontracts.dto.ResponseListDto
import com.louisgautier.apicontracts.dto.SimpleDictionaryDto
import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.server.database.entity.DictionaryTable.code
import com.louisgautier.server.database.entity.DictionaryTable.level
import com.louisgautier.server.database.source.DictionaryDataSource
import com.louisgautier.server.domain.mapper.toDictionary
import com.louisgautier.server.domain.mapper.toDictionaryEntity
import com.louisgautier.server.domain.mapper.toDictionaryWithGraphic
import com.louisgautier.server.domain.mapper.toDto
import com.louisgautier.server.domain.mapper.toEntity
import com.louisgautier.server.domain.mapper.toSimpleDictionary
import com.louisgautier.server.domain.repo.DictionaryRepository
import com.louisgautier.server.error.missingParameter
import org.jetbrains.exposed.sql.count

class DefaultDictionaryRepository(
    private val source: DictionaryDataSource
) : DictionaryRepository {

    override suspend fun exist(): Boolean {
        return source.exist()
    }

    override suspend fun getLevelCount(): List<LevelCountDto> {
        return source.getLevelCount()
            .map { row ->
                LevelCountDto(row[level].toDto(), row[code.count()].toInt())
            }
    }

    override suspend fun getRandomCharacters(
        params: EndPoint.GenerateSession
    ): List<DictionaryWithGraphicDto> {

        return source.getRandomCharacters(
            levels = params.levels?.map { it.toEntity() } ?: throw missingParameter("levels"),
            limit = params.limit
        ).map { it.toDictionaryWithGraphic() }
    }

    override suspend fun getByLevel(
        params: EndPoint.Characters.ByLevel
    ): ResponseListDto<SimpleDictionaryDto> {
        val (hasNextPage, data) = source.getByLevel(
            page = params.page,
            limit = params.limit,
            level = params.level?.toEntity() ?: throw missingParameter("level"),
        )
        return ResponseListDto(hasNextPage, data.map { it.toSimpleDictionary() })
    }

    override suspend fun search(
        params: EndPoint.Characters.Search
    ): ResponseListDto<SimpleDictionaryDto> {
        val (hasNextPage, data) = source.search(
            levels = params.levels?.map { it.toEntity() } ?: throw missingParameter("levels"),
            query = params.query,
            page = params.page,
            limit = params.limit
        )
        return ResponseListDto(hasNextPage, data.map { it.toSimpleDictionary() })
    }

    override suspend fun get(
        params: EndPoint.Characters.ByName
    ): DictionaryDto? {
        return source.get(params.code)?.toDictionary()
    }

    override suspend fun batchCreate(dictionary: List<DictionaryDto>) {
        source.batchInsert(dictionary.map { it.toDictionaryEntity() })
    }
}