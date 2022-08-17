package com.example.presentation.views

import androidx.lifecycle.*
import com.example.domain.model.Center
import com.example.domain.usecase.DeleteAllCentersUseCase
import com.example.domain.usecase.GetCenterListUseCase
import com.example.domain.usecase.ReadCenterListUseCase
import com.example.domain.usecase.WriteCenterListUseCase
import com.example.domain.util.Result
import com.example.domain.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCenterListUseCase: GetCenterListUseCase,
    private val writeCenterListUseCase: WriteCenterListUseCase,
    private val deleteAllCentersUseCase: DeleteAllCentersUseCase
): ViewModel() {
    private val _problem = MutableLiveData<Result<Any>>()
    val problem: LiveData<Result<Any>> get() = _problem

    // 이 값을 통해 10개의 페이지의 저장 진행률을 나타냄
    // ex) 3 -> 30%, 10 -> 100%
    private val _progressCount = MutableLiveData(0)
    val progressCount: LiveData<Int> get() = _progressCount

    // 가장 먼저 저장된 db를 삭제하고 작업 시작
    init {
        deleteAllCenters()
    }

    private fun getCenterList() {
        for(page in 1..10) {
            viewModelScope.launch {
                getCenterListUseCase(page)
                    .catch { _problem.postValue(Result.fail()) }
                    .collect {
                        if(it.isEmpty()) {
                            _problem.postValue(Result.error("Response is empty", it))
                        } else {
                            _problem.postValue(Result.success(it))
                            writeCenterList(it)
                        }
                    }
            }
        }
    }

    private fun writeCenterList(centerList: List<Center>) {
        viewModelScope.launch {
            val result = writeCenterListUseCase(centerList)
            if(result.status == Status.ERROR) {
                _problem.postValue(result)
            } else {
                // 저장을 성공적으로 완료하면 진행률을 나타내는 값을 +1 한다.
                _progressCount.value = _progressCount.value?.plus(1)
            }
        }
    }

    private fun deleteAllCenters() {
        viewModelScope.launch {
            val result = deleteAllCentersUseCase()
            if(result.status == Status.ERROR) {
                _problem.postValue(result)
            } else {
                getCenterList()
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel_Corona"
    }
}