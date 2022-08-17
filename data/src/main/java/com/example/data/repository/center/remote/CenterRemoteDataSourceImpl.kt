package com.example.data.repository.center.remote

import com.example.data.api.CenterApi
import com.example.data.model.CenterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CenterRemoteDataSourceImpl @Inject constructor(
    private val centerApi: CenterApi
) : CenterRemoteDataSource {

    override suspend fun getCenterList(page: Int): Flow<CenterEntity> {
        return flow {
            emit(centerApi.getCenterList(page))
        }
    }
}