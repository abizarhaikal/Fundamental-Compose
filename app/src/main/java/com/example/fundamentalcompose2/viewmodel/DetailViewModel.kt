package com.example.fundamentalcompose2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fundamentalcompose2.ResultState
import com.example.fundamentalcompose2.data.domain.repository.Repository
import com.example.fundamentalcompose2.data.response.ResponseDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _isDetail = MutableLiveData<ResultState<ResponseDetail>>()
    val isDetail: LiveData<ResultState<ResponseDetail>> = _isDetail

    fun getEventDetail(id: Int) {
        viewModelScope.launch {
            _isDetail.value = ResultState.Loading
            val result = repository.getEventDetail(id)
            _isDetail.value = result
        }
    }
}