package xyz.luko.server.domain.repo

import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.LevelCountDto
import xyz.luko.apicontracts.dto.ResponseListDto
import xyz.luko.apicontracts.dto.SimpleDictionaryDto
import xyz.luko.apicontracts.routing.EndPoint

interface DictionaryRepository {
    suspend fun exist(): Boolean

    suspend fun getLevelCount(): List<LevelCountDto>

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
