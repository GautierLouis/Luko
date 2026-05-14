package xyz.luko.server.database.source

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll
import xyz.luko.server.database.entity.GraphicTable
import xyz.luko.server.database.suspendTransaction
import xyz.luko.server.domain.model.GraphicEntity

class DefaultGraphicDataSource : GraphicDataSource {

    override suspend fun exist(): Boolean {
        return suspendTransaction {
            GraphicTable.selectAll().count() > 0L
        }
    }

    override suspend fun get(code: Int): ResultRow? {
        return suspendTransaction {
            GraphicTable.selectAll()
                .where { GraphicTable.code eq code }
                .limit(1)
                .firstOrNull()
        }
    }

    override suspend fun batchCreate(data: List<GraphicEntity>) {
        suspendTransaction {
            GraphicTable.batchInsert(
                data,
                ignore = true,
                shouldReturnGeneratedValues = false
            ) {
                this[GraphicTable.code] = it.code
                this[GraphicTable.strokes] = it.strokes
                this[GraphicTable.medians] = it.medians
            }
        }
    }
}