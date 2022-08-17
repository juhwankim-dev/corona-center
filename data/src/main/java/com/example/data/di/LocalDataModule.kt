package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.CenterDao
import com.example.data.db.CenterDatabase
import com.example.data.repository.center.local.CenterLocalDataSource
import com.example.data.repository.center.local.CenterLocalDataSourceImpl
import com.example.domain.util.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDataModule {
    @Provides
    @Singleton
    fun provideCenterDatabase(@ApplicationContext context: Context): CenterDatabase {
        return Room.databaseBuilder(
            context,
            CenterDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideCenterDao(centerDatabase: CenterDatabase): CenterDao {
        return centerDatabase.centerDao()
    }

    @Provides
    @Singleton
    fun provideCenterLocalDataSource(centerDao: CenterDao): CenterLocalDataSource {
        return CenterLocalDataSourceImpl(centerDao)
    }
}