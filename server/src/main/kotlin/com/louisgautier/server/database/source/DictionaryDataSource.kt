package com.louisgautier.server.database.source

import com.louisgautier.server.domain.model.CharacterFrequencyLevelEntity
import com.louisgautier.server.domain.model.DictionaryEntity
import org.jetbrains.exposed.sql.ResultRow

interface DictionaryDataSource {
    suspend fun getLevelCount(): List<ResultRow>

    suspend fun getRandomCharacters(
        levels: List<CharacterFrequencyLevelEntity>,
        limit: Int
    ): List<ResultRow>

    suspend fun getByLevel(
        page: Int,
        limit: Int,
        level: CharacterFrequencyLevelEntity
    ): Pair<Boolean, List<ResultRow>>

    suspend fun search(
        levels: List<CharacterFrequencyLevelEntity>,
        query: String,
        page: Int,
        limit: Int
    ): Pair<Boolean, List<ResultRow>>

    suspend fun get(
        code: Int
    ): ResultRow?

    suspend fun batchInsert(
        data: List<DictionaryEntity>
    )
}