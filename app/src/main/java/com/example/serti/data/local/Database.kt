package com.example.serti.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        SessionEntity::class,
        KeywordEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun keywordDao(): KeywordDao
}
