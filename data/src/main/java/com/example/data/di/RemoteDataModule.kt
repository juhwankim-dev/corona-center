package com.example.data.di

import com.example.data.api.CenterApi
import com.example.data.repository.center.remote.CenterRemoteDataSource
import com.example.data.repository.center.remote.CenterRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {
    @Provides
    @Singleton
    fun provideCenterRemoteDataSource(centerApi: CenterApi): CenterRemoteDataSource {
        return CenterRemoteDataSourceImpl(centerApi)
    }
}