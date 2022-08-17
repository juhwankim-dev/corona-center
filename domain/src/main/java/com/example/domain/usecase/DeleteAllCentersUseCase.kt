package com.example.domain.usecase

import com.example.domain.repository.CenterRepository
import com.example.domain.util.Result
import javax.inject.Inject

class DeleteAllCentersUseCase @Inject constructor(
    private val centerRepository: CenterRepository
) {
    suspend operator fun invoke(): Result<Any> = centerRepository.deleteAllCenters()
}