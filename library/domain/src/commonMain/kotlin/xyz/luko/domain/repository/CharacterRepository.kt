package xyz.luko.domain.repository

import xyz.luko.domain.mapper.LevelCount
import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.DictionaryWithGraphic
import xyz.luko.domain.model.Graphic
import xyz.luko.domain.model.ResponseList
import xyz.luko.domain.model.SimpleDictionary

interface CharacterRepository {
    suspend fun getLevelCount(): Result<List<LevelCount>>

    suspend fun generateSession(
        level: List<CharacterFrequencyLevel>,
        limit: Int,
    ): Result<List<DictionaryWithGraphic>>

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

    suspend fun getByName(code: Int): Result<DictionaryWithGraphic>

    suspend fun getGraphic(code: Int): Result<Graphic>
}
