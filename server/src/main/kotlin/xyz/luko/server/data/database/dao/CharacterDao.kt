package xyz.luko.server.data.database.dao

import org.jetbrains.exposed.v1.core.JoinType
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import xyz.luko.server.data.database.table.CharacterComplexityTable
import xyz.luko.server.data.database.table.DictionaryTable

interface CharacterDao {
    suspend fun get(codes: List<Int>): List<ResultRow>
}

internal class DefaultCharacterDao : CharacterDao {

    override suspend fun get(codes: List<Int>): List<ResultRow> {
        return suspendTransaction {
            CharacterComplexityTable
                .join(
                    otherTable = DictionaryTable,
                    joinType = JoinType.INNER,
                    onColumn = CharacterComplexityTable.code,
                    otherColumn = DictionaryTable.code
                )
                .select(
                    DictionaryTable.code,
                    DictionaryTable.medians,
                    CharacterComplexityTable.complexityFactor,
                )
                .where { CharacterComplexityTable.code inList codes }
                .toList()
        }
    }
}

