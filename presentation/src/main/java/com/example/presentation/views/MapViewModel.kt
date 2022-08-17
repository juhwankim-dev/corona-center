package com.example.presentation.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.domain.model.Center
import com.example.domain.usecase.ReadCenterListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val readCenterListUseCase: ReadCenterListUseCase
): ViewModel() {
    fun readCenterList(): LiveData<List<Center>> {
        return readCenterListUseCase().asLiveData()
    }
}