package xyz.luko.domain.repository

import xyz.luko.domain.mapper.toDomain
import xyz.luko.domain.mapper.toDto
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.model.ResponseList
import xyz.luko.domain.model.SimpleDictionary
import xyz.luko.network.interfaces.CharacterService

interface DictionaryRepository {
    suspend fun getByLevel(
        level: CharacterFrequencyLevel,
        page: Int,
        limit: Int,
    ): Result<ResponseList<SimpleDictionary>>

    suspend fun search(
        levels: List<CharacterFrequencyLevel>,
        query: String,
        page: Int,
        limit: Int,
    ): Result<ResponseList<SimpleDictionary>>

    suspend fun getByName(code: Int): Result<Dictionary>
}

internal class DefaultDictionaryRepository(
    private val characterService: CharacterService,
) : DictionaryRepository {

    override suspend fun getByLevel(
        level: CharacterFrequencyLevel,
        page: Int,
        limit: Int,
    ) = characterService
        .getByLevel(level.toDto(), page, limit)
        .map { response -> response.toDomain { it.toDomain() } }

    override suspend fun search(
        levels: List<CharacterFrequencyLevel>,
        query: String,
        page: Int,
        limit: Int,
    ) = characterService
        .search(levels.map { it.toDto() }, query, page, limit)
        .map { response -> response.toDomain { it.toDomain() } }

    override suspend fun getByName(code: Int): Result<Dictionary> =
        characterService.getByName(code).map { it.toDomain() }
}

