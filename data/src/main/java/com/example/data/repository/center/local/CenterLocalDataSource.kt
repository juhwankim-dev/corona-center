package com.example.data.repository.center.local

import com.example.domain.model.Center
import kotlinx.coroutines.flow.Flow

interface CenterLocalDataSource {
    fun readCenterList(): Flow<List<Center>>
    suspend fun writeCenter(centerList: List<Center>)
    suspend fun deleteAllCenters()
}