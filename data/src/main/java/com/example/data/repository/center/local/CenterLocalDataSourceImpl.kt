package com.example.data.repository.center.local

import com.example.data.db.CenterDao
import com.example.domain.model.Center
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CenterLocalDataSourceImpl @Inject constructor(
    private val dao: CenterDao
): CenterLocalDataSource {
    override fun readCenterList(): Flow<List<Center>> {
        return dao.readCenterList()
    }

    override suspend fun writeCenter(centerList: List<Center>) {
        return dao.writeCenter(centerList)
    }

    override suspend fun deleteAllCenters() {
        return dao.deleteAllCenters()
    }
}