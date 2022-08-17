package com.example.data.mapper

import com.example.data.model.CenterEntity
import com.example.domain.model.Center

object CenterMapper {
    operator fun invoke(centerEntity: CenterEntity): List<Center> {
        return centerEntity.data.toList().map {
            Center(
                id = it.id,
                address = it.address,
                centerName = it.centerName,
                facilityName = it.facilityName,
                phoneNumber = it.phoneNumber,
                updatedAt = it.updatedAt,
                lat = it.lat,
                lng = it.lng
            )
        }
    }
}