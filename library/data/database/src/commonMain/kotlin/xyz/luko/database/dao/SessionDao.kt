package xyz.luko.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import xyz.luko.database.entity.BasicStatistics
import xyz.luko.database.entity.SessionEntity
import xyz.luko.database.entity.SessionResponseEntity

@Dao
interface SessionDao {
    @Transaction
    suspend fun insertSessionWithResponses(
        session: SessionEntity,
        responses: List<SessionResponseEntity>,
    ) {
        val sessionId = insertSession(session)
        insertResponses(responses.map { it.copy(sessionId = sessionId) })
    }

    @Insert
    suspend fun insertSession(session: SessionEntity): Long

    @Insert
    suspend fun insertResponses(responses: List<SessionResponseEntity>)

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

    @Query("SELECT AVG(overallAccuracy) FROM SessionResponseEntity WHERE code = :code")
    suspend fun getAverageAccuracy(code: Int): Float?

    @Query(
        """
        SELECT
            COALESCE(SUM(score), 0) AS totalScore,
            COALESCE(AVG(duration), 0) AS averageTime,
            COALESCE(COUNT(*), 0) AS sessionCount,
            GROUP_CONCAT(difficulty) AS difficulties,
            GROUP_CONCAT(DISTINCT date) AS uniqueDates
        FROM SessionEntity
    """,
    )
    fun getBasicStatistics(): Flow<BasicStatistics>

    @Query(
        """
        SELECT
            GROUP_CONCAT(DISTINCT date) AS uniqueDates
        FROM SessionEntity
    """,
    )
    suspend fun getUniqueDates(): List<String>

    @Query(
        """
    SELECT DISTINCT date
    FROM SessionEntity
    WHERE date >= :weekStart
      AND date < :weekEnd
    ORDER BY date ASC
    """
    )
    suspend fun getSessionDatesForWeek(
        weekStart: String,
        weekEnd: String,
    ): List<String>
}
