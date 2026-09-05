package xyz.luko.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import xyz.luko.database.dao.CharacterLevelDao
import xyz.luko.database.dao.SessionDao
import xyz.luko.database.dao.SessionResponseDao
import xyz.luko.database.dao.SyncSessionDao
import xyz.luko.database.entity.CharacterLevelEntity
import xyz.luko.database.entity.SessionEntity
import xyz.luko.database.entity.SessionResponseEntity

@Database(
    entities = [
        SessionEntity::class,
        SessionResponseEntity::class,
        CharacterLevelEntity::class,
    ],
    version = 2,
)
@TypeConverters(RoomTypeConverters::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getSessionDao(): SessionDao
    abstract fun getSessionResponseDao(): SessionResponseDao
    abstract fun getSyncSession(): SyncSessionDao
    abstract fun getCharacterLevelDao(): CharacterLevelDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
internal expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
