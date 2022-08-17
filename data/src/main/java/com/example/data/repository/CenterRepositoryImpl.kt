package com.example.data.repository

import com.example.data.mapper.CenterMapper
import com.example.data.repository.center.local.CenterLocalDataSource
import com.example.data.repository.center.remote.CenterRemoteDataSource
import com.example.domain.model.Center
import com.example.domain.repository.CenterRepository
import com.example.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CenterRepositoryImpl @Inject constructor(
    private val centerRemoteDataSource: CenterRemoteDataSource,
    private val centerLocalDataSource: CenterLocalDataSource
) : CenterRepository {

    override suspend fun getCenterList(page: Int): Flow<List<Center>> {
        return flow {
            centerRemoteDataSource.getCenterList(page).collect {
                emit(CenterMapper(it))
            }
        }
    }

    override fun readCenterList(): Flow<List<Center>> {
        return centerLocalDataSource.readCenterList()
    }

    override suspend fun writeCenter(center: List<Center>): Result<Any> {
        return try {
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                centerLocalDataSource.writeCenter(center)
            }
            Result.success(null)
        } catch (error: Exception) {
            Result.error(error.message.toString(), null)
        }
    }

    override suspend fun deleteAllCenters(): Result<Any> {
        return try {
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                centerLocalDataSource.deleteAllCenters()
            }
            Result.success(null)
        } catch (error: Exception) {
            Result.error(error.message.toString(), null)
        }
    }
}