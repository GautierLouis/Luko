package xyz.luko.domain.repository

import xyz.luko.domain.mapper.toDomain
import xyz.luko.domain.mapper.toDto
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.Dictionary
import xyz.luko.network.interfaces.CharacterService

internal class DefaultDictionaryRepository(
    private val characterService: CharacterService,
) : DictionaryRepository {

    override suspend fun generateSession(
        level: List<CharacterFrequencyLevel>,
        limit: Int,
    ) = characterService
        .generateSession(level.map { it.toDto() }, limit)
        .map { it.map { dto -> dto.toDomain() } }

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
