package com.example.faydemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.faydemo.database.models.ProductDao
import com.example.faydemo.database.models.ProductEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Database(
    entities = [
        ProductEntity::class,
    ],
    version = 1,
    autoMigrations = [

    ],
    exportSchema = true
)
@TypeConverters
abstract class Database : RoomDatabase() {
    // abstract fun *dao(): *dao  | for each dao
    abstract fun ProductDao(): ProductDao

}

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesProductDao(database: com.example.faydemo.database.Database): ProductDao =
        database.ProductDao()

}