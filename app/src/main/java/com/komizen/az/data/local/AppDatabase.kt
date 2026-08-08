package com.komizen.az.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.komizen.az.data.model.Extension

@Database(
    entities = [Extension::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun extensionDao(): ExtensionDao

    companion object {
        const val DATABASE_NAME = "komizen_database"
    }
}