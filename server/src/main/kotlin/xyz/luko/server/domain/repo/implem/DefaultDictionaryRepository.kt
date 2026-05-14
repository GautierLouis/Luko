package xyz.luko.server.domain.repo.implem

import org.jetbrains.exposed.sql.count
import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.DictionaryWithGraphicDto
import xyz.luko.apicontracts.dto.LevelCountDto
import xyz.luko.apicontracts.dto.ResponseListDto
import xyz.luko.apicontracts.dto.SimpleDictionaryDto
import xyz.luko.apicontracts.routing.EndPoint
import xyz.luko.server.database.entity.DictionaryTable.code
import xyz.luko.server.database.entity.DictionaryTable.level
import xyz.luko.server.database.source.DictionaryDataSource
import xyz.luko.server.domain.mapper.toDictionary
import xyz.luko.server.domain.mapper.toDictionaryEntity
import xyz.luko.server.domain.mapper.toDictionaryWithGraphic
import xyz.luko.server.domain.mapper.toDto
import xyz.luko.server.domain.mapper.toEntity
import xyz.luko.server.domain.mapper.toSimpleDictionary
import xyz.luko.server.domain.repo.DictionaryRepository
import xyz.luko.server.error.missingParameter

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