package xyz.luko.network.interfaces

import xyz.luko.apicontracts.dto.CharacterFrequencyLevelDto
import xyz.luko.apicontracts.dto.DictionaryWithGraphicDto
import xyz.luko.apicontracts.dto.GraphicDto
import xyz.luko.apicontracts.dto.LevelCountDto
import xyz.luko.apicontracts.dto.ResponseListDto
import xyz.luko.apicontracts.dto.SimpleDictionaryDto

interface CharacterService {
    suspend fun getLevelCount(): Result<List<LevelCountDto>>

    suspend fun generateSession(
        level: List<CharacterFrequencyLevelDto>,
        limit: Int,
    ): Result<List<DictionaryWithGraphicDto>>

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

    suspend fun getByName(code: Int): Result<DictionaryWithGraphicDto>

    suspend fun getGraphic(code: Int): Result<GraphicDto>
}
