package xyz.luko.domain.repository

import xyz.luko.domain.mapper.LevelCount
import xyz.luko.domain.mapper.toDomain
import xyz.luko.domain.mapper.toDto
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.DictionaryWithGraphic
import xyz.luko.network.interfaces.CharacterService

internal class DefaultCharacterRepository(
    private val characterService: CharacterService,
) : CharacterRepository {
    override suspend fun getLevelCount(): Result<List<LevelCount>> =
        characterService
            .getLevelCount()
            .map { list ->
                list
                    .map { it.toDomain() }
                    .filter { it.level in CharacterFrequencyLevel.validEntry }
                    .sortedBy { it.level.ordinal }
            }

    override suspend fun generateSession(
        level: List<CharacterFrequencyLevel>,
        limit: Int,
    ) = characterService
        .generateSession(level.map { it.toDto() }, limit)
        .map { it.map { dto -> dto.toDomain() } }
        .mapCatching { list ->
            check(list.isNotEmpty()) { "Session is empty" }
            list
        }

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

    override suspend fun getByName(code: Int): Result<DictionaryWithGraphic> =
        characterService.getByName(code).map { it.toDomain() }

    override suspend fun getGraphic(code: Int) =
        characterService
            .getGraphic(code)
            .map { it.toDomain() }
}
