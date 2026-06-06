package xyz.luko.server.data.database.dao

import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Random
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import xyz.luko.server.data.database.StatementMapping.add
import xyz.luko.server.data.database.defaultWhere
import xyz.luko.server.data.database.insertAll
import xyz.luko.server.data.database.matchesPinyin
import xyz.luko.server.data.database.paginated
import xyz.luko.server.data.database.suspendTransaction
import xyz.luko.server.data.database.table.CharacterTable
import xyz.luko.server.data.database.table.DictionaryTable
import xyz.luko.server.data.database.table.GraphicTable
import xyz.luko.server.domain.model.DictionaryRow
import xyz.luko.server.domain.model.PaginatedRow

interface DictionaryDao {
    suspend fun batchInsert(data: List<DictionaryRow>)
    suspend fun createSession(levels: List<Int>, limit: Int, seed: Long): List<ResultRow>
    suspend fun getByLevel(page: Int, limit: Int, level: Int): PaginatedRow
    suspend fun search(levels: List<Int>, query: String, page: Int, limit: Int): PaginatedRow
    suspend fun get(code: Int): ResultRow?
}

// --- Implementation ---

internal class DefaultDictionaryDao : DictionaryDao {

    override suspend fun createSession(
        levels: List<Int>,
        limit: Int,
        seed: Long
    ): List<ResultRow> {
        return suspendTransaction {

            exec("SELECT setseed(${seed.toPostgresSeed()})")
            DictionaryTable
                .join(CharacterTable, JoinType.INNER, DictionaryTable.code, CharacterTable.code)
                .join(GraphicTable, JoinType.INNER, DictionaryTable.code, GraphicTable.code)
                .selectAll()
                .where { DictionaryTable.level inList levels }
                .orderBy(Random())
                .limit(limit)
                .toList()
        }
    }

    override suspend fun getByLevel(
        page: Int,
        limit: Int,
        level: Int
    ): PaginatedRow {
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
    ): PaginatedRow {
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
                    DictionaryTable.sound
                )
                .defaultWhere { DictionaryTable.code eq code }
                .limit(1)
                .firstOrNull()
        }
    }

    override suspend fun batchInsert(data: List<DictionaryRow>) {
        DictionaryTable.insertAll(data) { dictionary -> this.add(dictionary) }
    }

    // seed must be between -1.0 and 1.0 for Postgres setseed()
    private fun Long.toPostgresSeed(): Double = (this % 1_000_000) / 1_000_000.0
}
