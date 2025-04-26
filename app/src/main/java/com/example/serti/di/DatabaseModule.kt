package com.example.serti.di

import android.content.Context
import androidx.room.Room
import com.example.serti.data.local.AppDatabase
import com.example.serti.data.local.KeywordDao
import com.example.serti.data.local.SessionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "app_db").build()

    @Provides @Singleton
    fun provideSessionDao(db: AppDatabase): SessionDao = db.sessionDao()

    @Provides
    @Singleton
    fun provideKeywordDao(db: AppDatabase): KeywordDao =
        db.keywordDao()
}
