package com.example.domain.usecase

import com.example.domain.model.Center
import com.example.domain.repository.CenterRepository
import com.example.domain.util.Result
import javax.inject.Inject

class WriteCenterListUseCase @Inject constructor(
    private val centerRepository: CenterRepository
) {
    suspend operator fun invoke(center: List<Center>): Result<Any> = centerRepository.writeCenter(center)
}
