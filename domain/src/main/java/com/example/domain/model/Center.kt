package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.util.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class Center(
    @PrimaryKey
    val id: Int,
    val address: String,
    val centerName: String,
    val facilityName: String,
    val phoneNumber: String,
    val updatedAt: String,
    val centerType: String,
    val lat: Double,
    val lng: Double
)
