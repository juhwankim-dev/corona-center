package com.example.domain.repository

import com.example.domain.model.Center
import com.example.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CenterRepository {
    suspend fun getCenterList(page: Int): Flow<List<Center>>
    fun readCenterList(): Flow<List<Center>>
    suspend fun writeCenter(center: List<Center>): Result<Any>
    suspend fun deleteAllCenters(): Result<Any>
}