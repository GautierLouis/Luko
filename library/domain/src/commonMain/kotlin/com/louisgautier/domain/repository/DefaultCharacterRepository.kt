package com.louisgautier.domain.repository

import com.louisgautier.domain.mapper.LevelCount
import com.louisgautier.domain.mapper.toDomain
import com.louisgautier.domain.mapper.toDto
import com.louisgautier.domain.model.CharacterFrequencyLevel
import com.louisgautier.domain.model.DictionaryWithGraphic
import com.louisgautier.network.interfaces.CharacterService

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
