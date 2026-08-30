package xyz.luko.database.dao

import androidx.room.Dao
import androidx.room.Query
import xyz.luko.database.entity.CharacterLevelEntity

@Dao
interface CharacterLevelDao {

    @Query("SELECT * FROM CharacterLevelEntity WHERE code IN (:codes)")
    suspend fun getByCodes(codes: List<Int>): List<CharacterLevelEntity>
}
