package com.louisgautier.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

interface AppDatabaseBuilder {
    fun createDatabase(databaseFileName: String = "app_database.db"): RoomDatabase.Builder<AppDatabase>
}

class DatabaseProvider(
    private val builder: AppDatabaseBuilder
) {
    fun getDatabase(): AppDatabase = builder.createDatabase()
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}


