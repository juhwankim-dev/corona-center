package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.domain.model.Center
import com.example.domain.util.Constants.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface CenterDao {
    @Query("SELECT * FROM $TABLE_NAME")
    fun readCenterList(): Flow<List<Center>>

    @Insert
    suspend fun writeCenter(centerList: List<Center>)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAllCenters()
}