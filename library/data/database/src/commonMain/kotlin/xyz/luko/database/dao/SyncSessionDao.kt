package xyz.luko.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import xyz.luko.database.entity.CharacterLevelEntity
import xyz.luko.database.entity.SessionResponseEntity


@Dao
interface SyncSessionDao {

    @Transaction
    suspend fun sync(
        sessionId: Long,
        accuracy: Double,
        responses: List<SessionResponseEntity>,
        levelsToInsert: List<CharacterLevelEntity>,
        levelsToUpdate: List<CharacterLevelEntity>,
    ) {
        markSynced(sessionId, accuracy)
        updateResponses(responses)
        if (levelsToInsert.isNotEmpty()) insertAll(levelsToInsert)
        if (levelsToUpdate.isNotEmpty()) updateAll(levelsToUpdate)
    }

    /**
     * Don't use directly. Only call from within [sync] — needs to run inside the same transaction.
     */
    @Query("UPDATE SessionEntity SET accuracy = :accuracy, isSync = 1 WHERE id = :sessionId")
    suspend fun markSynced(sessionId: Long, accuracy: Double)

    /**
     * Don't use directly. Only call from within [sync] — needs to run inside the same transaction.
     */
    @Update
    suspend fun updateResponses(responses: List<SessionResponseEntity>)

    /**
     * Don't use directly. Only call from within [sync] — needs to run inside the same transaction.
     */
    @Update
    suspend fun updateAll(entities: List<CharacterLevelEntity>)

    /**
     * Don't use directly. Only call from within [sync] — needs to run inside the same transaction.
     */
    @Insert
    suspend fun insertAll(entities: List<CharacterLevelEntity>)
}
