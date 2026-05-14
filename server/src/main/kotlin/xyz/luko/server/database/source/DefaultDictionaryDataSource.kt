package xyz.luko.server.database.source

import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.selectAll
import xyz.luko.server.database.defaultWhere
import xyz.luko.server.database.entity.DictionaryTable
import xyz.luko.server.database.joinGraphic
import xyz.luko.server.database.matchesPinyin
import xyz.luko.server.database.paginated
import xyz.luko.server.database.suspendTransaction
import xyz.luko.server.domain.model.CharacterFrequencyLevelEntity
import xyz.luko.server.domain.model.DictionaryEntity
import kotlin.random.Random

class DefaultDictionaryDataSource : DictionaryDataSource {
    override suspend fun exist(): Boolean {
        return suspendTransaction {
            DictionaryTable.selectAll().count() > 0L
        }
    }

    override suspend fun getLevelCount(): List<ResultRow> {
        return suspendTransaction {
            DictionaryTable
                .select(DictionaryTable.level, DictionaryTable.code.count())
                .groupBy(DictionaryTable.level)
                .toList()
        }
    }

    override suspend fun getRandomCharacters(
        levels: List<CharacterFrequencyLevelEntity>,
        limit: Int
    ): List<ResultRow> {
        return suspendTransaction {
            DictionaryTable
                .joinGraphic()
                .selectAll()
                .defaultWhere { DictionaryTable.level inList levels }
                .let { query ->
                    val total = query.count()
                    val offset =
                        if (total > limit) Random.nextLong(0, total - limit) else 0L
                    query.limit(limit).offset(offset)
                }
                .toList()
        }
    }

    override suspend fun getByLevel(
        page: Int,
        limit: Int,
        level: CharacterFrequencyLevelEntity
    ): Pair<Boolean, List<ResultRow>> {
        return suspendTransaction {
            DictionaryTable.selectAll()
                .defaultWhere { DictionaryTable.level eq level }
                .paginated(page, limit)
        }
    }

    override suspend fun search(
        levels: List<CharacterFrequencyLevelEntity>,
        query: String,
        page: Int,
        limit: Int
    ): Pair<Boolean, List<ResultRow>> {
        return suspendTransaction {
            DictionaryTable.selectAll()
                .defaultWhere { DictionaryTable.level inList levels and matchesPinyin(query) }
                .paginated(page, limit)
        }
    }

    override suspend fun get(code: Int): ResultRow? {
        return suspendTransaction {
            DictionaryTable.selectAll()
                .where { DictionaryTable.code eq code }
                .limit(1)
                .firstOrNull()
        }
    }

    override suspend fun batchInsert(
        data: List<DictionaryEntity>
    ) {
        suspendTransaction {
            DictionaryTable.batchInsert(
                data = data,
                ignore = true,
                shouldReturnGeneratedValues = false
            ) { dictionary ->
                this[DictionaryTable.code] = dictionary.code
                this[DictionaryTable.definition] = dictionary.definition
                this[DictionaryTable.pinyin] = dictionary.pinyin
                this[DictionaryTable.decomposition] = dictionary.decomposition
                this[DictionaryTable.decompositionList] = dictionary.decompositionList
                this[DictionaryTable.level] = dictionary.level
                this[DictionaryTable.etymologyType] = dictionary.etymologyType
                this[DictionaryTable.etymologyPhonetic] = dictionary.etymologyPhonetic
                this[DictionaryTable.etymologySemantic] = dictionary.etymologySemantic
                this[DictionaryTable.etymologyHint] = dictionary.etymologyHint
                this[DictionaryTable.radical] = dictionary.radical
                this[DictionaryTable.matches] = Json.encodeToString(dictionary.matches)
            }
        }
    }
}