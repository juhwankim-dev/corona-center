package com.example.data.repository.center.remote

import com.example.data.model.CenterEntity
import kotlinx.coroutines.flow.Flow

interface CenterRemoteDataSource {
    suspend fun getCenterList(page: Int): Flow<CenterEntity>
}