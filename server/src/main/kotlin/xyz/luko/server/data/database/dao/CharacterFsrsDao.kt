package xyz.luko.server.data.database.dao

import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchUpsert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import xyz.luko.server.data.database.table.CharacterFsrsStateTable
import xyz.luko.server.domain.model.ProgressionRow
import kotlin.time.Clock

interface CharacterFsrsDao {
    suspend fun get(id: EntityID<Int>, codes: List<Int>): List<ResultRow>
    suspend fun getLevels(id: EntityID<Int>): List<ResultRow>

    suspend fun batchedInsertOrUpdate(
        id: EntityID<Int>,
        progression: List<ProgressionRow>,
        lastReviewedAt: Long,
    )
}


internal class DefaultCharacterFsrsDao : CharacterFsrsDao {
    override suspend fun get(id: EntityID<Int>, codes: List<Int>): List<ResultRow> {
        return suspendTransaction {
            CharacterFsrsStateTable
                .selectAll()
                .where {
                    (CharacterFsrsStateTable.userId eq id) and
                        (CharacterFsrsStateTable.characterCode inList codes)
                }.toList()
        }
    }

    /**
     * Warning: This method should be called in a transaction.
     */
    override suspend fun batchedInsertOrUpdate(
        id: EntityID<Int>,
        progression: List<ProgressionRow>,
        lastReviewedAt: Long,
    ) {
        CharacterFsrsStateTable.batchUpsert(
            data = progression,
            keys = arrayOf(
                CharacterFsrsStateTable.userId,
                CharacterFsrsStateTable.characterCode
            )
        ) { row ->
            this[CharacterFsrsStateTable.userId] = id
            this[CharacterFsrsStateTable.characterCode] = row.code
            this[CharacterFsrsStateTable.difficulty] = row.difficulty
            this[CharacterFsrsStateTable.stability] = row.stability
            this[CharacterFsrsStateTable.lastReviewedAt] = lastReviewedAt
            this[CharacterFsrsStateTable.nextReviewDueAt] = row.nextReviewDueAt
            this[CharacterFsrsStateTable.level] = row.level
            this[CharacterFsrsStateTable.updatedAt] = Clock.System.now().epochSeconds
        }
    }

    override suspend fun getLevels(id: EntityID<Int>): List<ResultRow> =
        suspendTransaction {
            CharacterFsrsStateTable
                .select(CharacterFsrsStateTable.level, CharacterFsrsStateTable.characterCode)
                .where { CharacterFsrsStateTable.userId eq id }
                .toList()
        }
}
