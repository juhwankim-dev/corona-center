package com.example.domain.usecase

import com.example.domain.model.Center
import com.example.domain.repository.CenterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadCenterListUseCase @Inject constructor(
    private val centerRepository: CenterRepository
) {
    operator fun invoke(): Flow<List<Center>> = centerRepository.readCenterList()
}