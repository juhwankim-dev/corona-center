package com.example.domain.usecase

import com.example.domain.model.Center
import com.example.domain.repository.CenterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCenterListUseCase @Inject constructor(
    private val centerRepository: CenterRepository
) {
    suspend operator fun invoke(page: Int): Flow<List<Center>> = centerRepository.getCenterList(page)
}