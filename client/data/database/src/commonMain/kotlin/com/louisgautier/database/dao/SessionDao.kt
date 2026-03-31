package com.louisgautier.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.louisgautier.database.entity.BasicStatistics
import com.louisgautier.database.entity.EmbeddedResponse
import com.louisgautier.database.entity.ResponseEntity
import com.louisgautier.database.entity.SessionEntity
import kotlinx.serialization.json.Json

@Dao
interface SessionDao {

    @Insert
    suspend fun insertSession(session: SessionEntity): Long

    @Insert
    suspend fun insertResponses(responses: List<ResponseEntity>)

    @Transaction
    suspend fun insertSessionWithResponses(
        session: SessionEntity,
        responses: List<EmbeddedResponse>,
    ) {
        val sessionId = insertSession(session)
        val responseEntities = responses.map { response ->
            ResponseEntity(
                sessionId = sessionId.toInt(),
                code = response.code,
                overallAccuracy = response.statistics.overallAccuracy,
                response = Json.encodeToString(response),
            )
        }
        insertResponses(responseEntities)
    }

    @Query("SELECT * FROM SessionEntity ORDER BY date DESC")
    suspend fun getAll(): List<SessionEntity>

    @Query("SELECT * FROM SessionEntity ORDER BY date DESC LIMIT :limit")
    suspend fun getLast(limit: Int): List<SessionEntity>

    @Query("""
        SELECT 
            s.* 
        FROM SessionEntity s 
        INNER JOIN ResponseEntity r 
            ON r.sessionId = s.id 
        WHERE r.code = :code 
        ORDER BY s.date 
        DESC LIMIT 3
    """)
    suspend fun getLastFor(code: Int): List<SessionEntity>

    @Query("SELECT AVG(overallAccuracy) FROM ResponseEntity WHERE code = :code")
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
    """
    )
    suspend fun getBasicStatistics(): BasicStatistics

}
