package xyz.luko.server.data.database.dao

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import xyz.luko.server.data.database.StatementMapping.add
import xyz.luko.server.data.database.insertAll
import xyz.luko.server.data.database.suspendTransaction
import xyz.luko.server.data.database.table.SeedTable
import xyz.luko.server.domain.model.SeedRow

interface SeedDao {
    suspend fun insertSeed(data: SeedRow)
    suspend fun getSeed(seed: Long): ResultRow?
}

// --- Implementation ---

internal class DefaultSeedDao : SeedDao {

    override suspend fun insertSeed(data: SeedRow) {
        SeedTable.insertAll(listOf(data)) { seed -> this.add(seed) }
    }

    override suspend fun getSeed(seed: Long): ResultRow? = suspendTransaction {
        SeedTable
            .selectAll()
            .where { SeedTable.seed eq seed }
            .limit(1)
            .firstOrNull()
    }
}
