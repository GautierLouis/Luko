package com.louisgautier.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.component.KoinComponent

class AndroidAppDataBaseBuilder(
    private val context: Context
) : AppDatabaseBuilder, KoinComponent {

    override fun createDatabase(databaseFileName: String): RoomDatabase.Builder<AppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(databaseFileName)
        return Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = dbFile.absolutePath,
        )
    }
}