package com.louisgautier.server.domain.repo

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import com.louisgautier.apicontracts.dto.DictionaryDto
import com.louisgautier.apicontracts.dto.DictionaryWithGraphicDto
import com.louisgautier.apicontracts.dto.LevelCountDto
import com.louisgautier.apicontracts.dto.ResponseListDto
import com.louisgautier.apicontracts.dto.SimpleDictionaryDto

interface DictionaryRepository {
    suspend fun getLevelCount(): List<LevelCountDto>

    suspend fun getRandomCharacters(
        levels: List<CharacterFrequencyLevelDto>,
        limit: Int
    ): List<DictionaryWithGraphicDto>

    suspend fun getAll(
        page: Int,
        limit: Int,
        levels: List<CharacterFrequencyLevelDto>
    ): ResponseListDto<DictionaryDto>

    suspend fun getByLevel(
        page: Int,
        limit: Int,
        level: CharacterFrequencyLevelDto
    ): ResponseListDto<SimpleDictionaryDto>

    suspend fun get(code: Int): DictionaryDto?

    suspend fun batchCreate(dictionary: List<DictionaryDto>)
}