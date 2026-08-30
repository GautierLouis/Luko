package xyz.luko.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import xyz.luko.database.entity.SessionEntity
import xyz.luko.database.entity.SessionResponseEntity

@Dao
interface SessionDao {
    @Transaction
    suspend fun insertSessionWithResponses(
        session: SessionEntity,
        responses: List<SessionResponseEntity>,
    ): Long {
        val sessionId = insertSession(session)
        insertResponses(responses.map { it.copy(sessionId = sessionId) })
        return sessionId
    }

    @Insert
    suspend fun insertSession(session: SessionEntity): Long

    @Insert
    suspend fun insertResponses(responses: List<SessionResponseEntity>)

    @Query("SELECT * FROM SessionEntity WHERE isSync = 0")
    suspend fun getUnsyncedSessions(): List<SessionEntity>

    @Query("SELECT * FROM SessionResponseEntity WHERE sessionId = :sessionId")
    suspend fun getResponsesForSession(sessionId: Long): List<SessionResponseEntity>

    @Query("SELECT * FROM SessionEntity ORDER BY date DESC")
    suspend fun getAll(): List<SessionEntity>

    @Query("SELECT * FROM SessionEntity WHERE id = :id")
    suspend fun get(id: Long): SessionEntity

    @Query("SELECT * FROM SessionEntity ORDER BY date DESC LIMIT :limit")
    fun getLast(limit: Int): Flow<List<SessionEntity>>

    @Query("SELECT * FROM SessionEntity ORDER BY date DESC LIMIT 1")
    suspend fun getLastSession(): SessionEntity?

    @Query("SELECT * FROM SessionEntity ORDER BY date DESC")
    fun getAllPaged(): PagingSource<Int, SessionEntity>

    @Query(
        """
        SELECT
            s.*
        FROM SessionEntity s
        INNER JOIN SessionResponseEntity r
            ON r.sessionId = s.id
        WHERE r.code = :code
        ORDER BY s.date
        DESC LIMIT 3
    """,
    )
    suspend fun getLastFor(code: Int): List<SessionEntity>

    @Query("SELECT DISTINCT substr(date, 1, 10) FROM SessionEntity WHERE substr(date, 1, 10) IN (:days)")
    suspend fun hasSessionFor(days: List<String>): List<String>
}
