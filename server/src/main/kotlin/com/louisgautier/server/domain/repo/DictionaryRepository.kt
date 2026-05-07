package com.louisgautier.server.domain.repo

import com.louisgautier.apicontracts.dto.DictionaryDto
import com.louisgautier.apicontracts.dto.DictionaryWithGraphicDto
import com.louisgautier.apicontracts.dto.LevelCountDto
import com.louisgautier.apicontracts.dto.ResponseListDto
import com.louisgautier.apicontracts.dto.SimpleDictionaryDto
import com.louisgautier.apicontracts.routing.EndPoint

interface DictionaryRepository {
    suspend fun exist(): Boolean

    suspend fun getLevelCount(): List<LevelCountDto>

    suspend fun getRandomCharacters(
        params: EndPoint.GenerateSession
    ): List<DictionaryWithGraphicDto>

    suspend fun getByLevel(
        params: EndPoint.Characters.ByLevel
    ): ResponseListDto<SimpleDictionaryDto>

    suspend fun search(
        params: EndPoint.Characters.Search
    ): ResponseListDto<SimpleDictionaryDto>

    suspend fun get(
        params: EndPoint.Characters.ByName
    ): DictionaryDto?

    suspend fun batchCreate(dictionary: List<DictionaryDto>)
}