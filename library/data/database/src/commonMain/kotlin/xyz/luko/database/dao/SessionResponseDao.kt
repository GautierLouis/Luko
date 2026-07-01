package xyz.luko.database.dao

import androidx.room.Dao
import androidx.room.Query
import xyz.luko.database.entity.SessionResponseEntity

@Dao
interface SessionResponseDao {

    @Query("SELECT * FROM SessionResponseEntity WHERE SessionResponseEntity.sessionId = :sessionId")
    suspend fun get(sessionId: Long): List<SessionResponseEntity>

    @Query("SELECT * FROM SessionResponseEntity WHERE code = :code")
    suspend fun getSimilar(code: Int): List<SessionResponseEntity>
}
