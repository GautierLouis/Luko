package com.louisgautier.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.louisgautier.database.dao.SessionDao
import com.louisgautier.database.entity.ResponseEntity
import com.louisgautier.database.entity.SessionEntity

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
