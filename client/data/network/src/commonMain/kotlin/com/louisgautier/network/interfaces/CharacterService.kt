package com.louisgautier.network.interfaces

import com.louisgautier.apicontracts.dto.CharacterFrequencyLevelDto
import com.louisgautier.apicontracts.dto.DictionaryWithGraphicDto
import com.louisgautier.apicontracts.dto.GraphicDto
import com.louisgautier.apicontracts.dto.LevelCountDto
import com.louisgautier.apicontracts.dto.ResponseListDto
import com.louisgautier.apicontracts.dto.SimpleDictionaryDto

interface CharacterService {

    suspend fun getLevelCount(): Result<List<LevelCountDto>>
    suspend fun generateSession(level: List<CharacterFrequencyLevelDto>, limit: Int): Result<List<DictionaryWithGraphicDto>>
    suspend fun getByLevel(level: CharacterFrequencyLevelDto, page: Int, limit: Int): Result<ResponseListDto<SimpleDictionaryDto>>
    suspend fun getByName(code: Int): Result<DictionaryWithGraphicDto>
    suspend fun getSVG(code: Int): Result<GraphicDto>

}