package com.example.data.di

import com.example.data.repository.CenterRepositoryImpl
import com.example.data.repository.center.local.CenterLocalDataSource
import com.example.data.repository.center.remote.CenterRemoteDataSource
import com.example.domain.repository.CenterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideCenterRepository(
        centerRemoteDataSource: CenterRemoteDataSource,
        centerLocalDataSource: CenterLocalDataSource
    ): CenterRepository {
        return CenterRepositoryImpl(centerRemoteDataSource, centerLocalDataSource)
    }
}