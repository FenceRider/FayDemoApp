package com.example.faydemo.di

import android.content.Context
import androidx.room.Room
import com.example.faydemo.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesBHDatabase(@ApplicationContext context: Context): Database {
        val file = context.getDatabasePath("fay-database.db")
        return Room.databaseBuilder(context, Database::class.java, file.absolutePath).build()
    }
}