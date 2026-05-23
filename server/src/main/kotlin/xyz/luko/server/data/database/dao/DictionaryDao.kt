package xyz.luko.server.data.database.dao

import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import xyz.luko.server.data.database.BatchInsertMapping.add
import xyz.luko.server.data.database.defaultWhere
import xyz.luko.server.data.database.insertAll
import xyz.luko.server.data.database.matchesPinyin
import xyz.luko.server.data.database.paginated
import xyz.luko.server.data.database.suspendTransaction
import xyz.luko.server.data.database.table.CharacterTable
import xyz.luko.server.data.database.table.DictionaryTable
import xyz.luko.server.data.database.table.GraphicTable
import xyz.luko.server.domain.model.DictionaryRow
import kotlin.random.Random

interface DictionaryDao {
    suspend fun batchInsert(data: List<DictionaryRow>)
    suspend fun generateSession(levels: List<Int>, limit: Int): List<ResultRow>

    //TODO Remove Pair<>
    suspend fun getByLevel(page: Int, limit: Int, level: Int): Pair<Boolean, List<ResultRow>>
    suspend fun search(
        levels: List<Int>,
        query: String,
        page: Int,
        limit: Int
    ): Pair<Boolean, List<ResultRow>>

    suspend fun get(code: Int): ResultRow?
}

// --- Implementation ---

internal class DefaultDictionaryDao : DictionaryDao {

    override suspend fun generateSession(
        levels: List<Int>,
        limit: Int
    ): List<ResultRow> {
        return suspendTransaction {
            DictionaryTable
                .join(CharacterTable, JoinType.INNER, DictionaryTable.code, CharacterTable.code)
                .join(GraphicTable, JoinType.INNER, DictionaryTable.code, GraphicTable.code)
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
        level: Int
    ): Pair<Boolean, List<ResultRow>> {
        return suspendTransaction {
            DictionaryTable.selectAll()
                .defaultWhere { DictionaryTable.level eq level }
                .paginated(page, limit)
        }
    }

    override suspend fun search(
        levels: List<Int>,
        query: String,
        page: Int,
        limit: Int
    ): Pair<Boolean, List<ResultRow>> {
        return suspendTransaction {
            DictionaryTable
                .join(CharacterTable, JoinType.INNER, DictionaryTable.code, CharacterTable.code)
                .selectAll()
                .defaultWhere { DictionaryTable.level inList levels and matchesPinyin(query) }
                .paginated(page, limit)
        }
    }

    override suspend fun get(code: Int): ResultRow? {
        return suspendTransaction {
            DictionaryTable
                .join(GraphicTable, JoinType.INNER, DictionaryTable.code, GraphicTable.code)
                .join(CharacterTable, JoinType.INNER, DictionaryTable.code, CharacterTable.code)
                .select(
                    DictionaryTable.code,
                    DictionaryTable.decomposition,
                    DictionaryTable.level,
                    DictionaryTable.medians,
                    GraphicTable.strokes,
                    CharacterTable.pinyin,
                )
                .defaultWhere { DictionaryTable.code eq code }
                .limit(1)
                .firstOrNull()
        }
    }

    override suspend fun batchInsert(data: List<DictionaryRow>) {
        DictionaryTable.insertAll(data) { dictionary -> this.add(dictionary) }
    }
}
