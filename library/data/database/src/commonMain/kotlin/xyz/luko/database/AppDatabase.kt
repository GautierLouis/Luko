package xyz.luko.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import xyz.luko.database.dao.SessionDao
import xyz.luko.database.entity.ResponseEntity
import xyz.luko.database.entity.SessionEntity

@Database(
    entities = [
        SessionEntity::class,
        ResponseEntity::class,
    ],
    version = 1,
)
@TypeConverters(RoomTypeConverters::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getSessionDao(): SessionDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
internal expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
