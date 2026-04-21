package com.louisgautier.server.database.source

import com.louisgautier.server.database.entity.GraphicTable
import com.louisgautier.server.database.suspendTransaction
import com.louisgautier.server.domain.model.GraphicEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll

class DefaultGraphicDataSource : GraphicDataSource {

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