package com.louisgautier.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

class JvmAppDatabaseBuilder: AppDatabaseBuilder {

    override fun createDatabase(databaseFileName: String): RoomDatabase.Builder<AppDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), databaseFileName)
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile.absolutePath,
        )
    }
}
