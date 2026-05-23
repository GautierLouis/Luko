package xyz.luko.domain.repository

import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.model.ResponseList
import xyz.luko.domain.model.SimpleDictionary

interface DictionaryRepository {
    suspend fun generateSession(
        level: List<CharacterFrequencyLevel>,
        limit: Int,
    ): Result<List<Dictionary>>

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

    suspend fun getByName(code: Int): Result<Dictionary>

}
