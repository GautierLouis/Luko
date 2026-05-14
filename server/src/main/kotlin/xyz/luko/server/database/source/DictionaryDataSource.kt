package xyz.luko.server.database.source

import org.jetbrains.exposed.sql.ResultRow
import xyz.luko.server.domain.model.CharacterFrequencyLevelEntity
import xyz.luko.server.domain.model.DictionaryEntity

interface DictionaryDataSource {
    suspend fun exist(): Boolean

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