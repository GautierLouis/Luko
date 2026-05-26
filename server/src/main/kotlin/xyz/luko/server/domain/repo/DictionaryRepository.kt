package xyz.luko.server.domain.repo

import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.ResponseListDto
import xyz.luko.apicontracts.dto.SimpleDictionaryDto
import xyz.luko.apicontracts.routing.Destination
import xyz.luko.server.data.database.dao.DictionaryDao
import xyz.luko.server.domain.mapper.DomainMapping.map
import xyz.luko.server.domain.mapper.ResultRowMapping.toDictionary
import xyz.luko.server.domain.mapper.ResultRowMapping.toSimpleDictionary
import xyz.luko.server.error.missingParameter

interface DictionaryRepository {
    suspend fun getByLevel(params: Destination.Characters.ByLevel): ResponseListDto<SimpleDictionaryDto>
    suspend fun search(params: Destination.Characters.Search): ResponseListDto<SimpleDictionaryDto>
    suspend fun get(params: Destination.Characters.ByName): DictionaryDto?
}

// --- Implementation ---

internal class DefaultDictionaryRepository(
    private val source: DictionaryDao,
) : DictionaryRepository {

    override suspend fun getByLevel(
        params: Destination.Characters.ByLevel
    ): ResponseListDto<SimpleDictionaryDto> =
        source.getByLevel(
            page = params.page,
            limit = params.limit,
            level = params.level?.value ?: throw missingParameter("level"),
        ).map { it.toSimpleDictionary() }


    override suspend fun search(
        params: Destination.Characters.Search
    ): ResponseListDto<SimpleDictionaryDto> =
        source.search(
            levels = params.levels?.map { it.value } ?: throw missingParameter("levels"),
            query = params.query,
            page = params.page,
            limit = params.limit
        ).map { it.toSimpleDictionary() }

    override suspend fun get(
        params: Destination.Characters.ByName
    ): DictionaryDto? = source.get(params.code)?.toDictionary()
}

