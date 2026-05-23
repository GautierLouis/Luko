package xyz.luko.network.interfaces

import xyz.luko.apicontracts.dto.CharacterFrequencyLevelDto
import xyz.luko.apicontracts.dto.DictionaryDto
import xyz.luko.apicontracts.dto.ResponseListDto
import xyz.luko.apicontracts.dto.SimpleDictionaryDto

interface CharacterService {
    suspend fun generateSession(
        level: List<CharacterFrequencyLevelDto>,
        limit: Int,
    ): Result<List<DictionaryDto>>

    suspend fun getByLevel(
        level: CharacterFrequencyLevelDto,
        page: Int,
        limit: Int,
    ): Result<ResponseListDto<SimpleDictionaryDto>>

    suspend fun search(
        levels: List<CharacterFrequencyLevelDto>,
        query: String,
        page: Int,
        limit: Int,
    ): Result<ResponseListDto<SimpleDictionaryDto>>

    suspend fun getByName(code: Int): Result<DictionaryDto>
}
