package xyz.luko.server.data.database.dao

import kotlinx.serialization.json.Json
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import xyz.luko.server.data.database.table.SessionResponseTable
import xyz.luko.server.data.database.table.SessionTable
import xyz.luko.server.domain.model.SessionRow

interface SessionDao {
    suspend fun insertSession(session: SessionRow)
    suspend fun getResponse(sessionId: EntityID<Int>, code: Int): ResultRow?
}

internal class DefaultSessionDao : SessionDao {
    override suspend fun insertSession(session: SessionRow) {
        suspendTransaction {
            val sessionId = SessionTable.insertAndGetId {
                it[userId] = session.userId
                it[date] = session.date
                it[offset] = session.offset
                it[duration] = session.duration
                it[difficulty] = session.difficulty
                it[questionsCount] = session.questionsCount
                it[accuracy] = session.accuracy
            }

            if (session.responses.isNotEmpty()) {
                SessionResponseTable.batchInsert(session.responses) { response ->
                    this[SessionResponseTable.sessionId] = sessionId
                    this[SessionResponseTable.code] = response.characterCode
                    this[SessionResponseTable.overallAccuracy] =
                        response.comparisonResult.overallAccuracy
                    this[SessionResponseTable.response] = Json.encodeToString(response)
                }
            }
        }
    }

    override suspend fun getResponse(
        sessionId: EntityID<Int>,
        code: Int,
    ): ResultRow? {
        return suspendTransaction {
            SessionResponseTable
                .selectAll()
                .where { SessionResponseTable.sessionId eq sessionId }
                .andWhere { SessionResponseTable.code eq code }
                .limit(1)
                .firstOrNull()
        }
    }
}
