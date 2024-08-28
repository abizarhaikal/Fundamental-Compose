package com.example.fundamentalcompose2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fundamentalcompose2.ResultState
import com.example.fundamentalcompose2.data.domain.repository.Repository
import com.example.fundamentalcompose2.data.response.ResponseEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _events = MutableLiveData<ResultState<ResponseEvents>>()
    val events: LiveData<ResultState<ResponseEvents>> = _events


    fun fetchEvents() {
        viewModelScope.launch {
            _events.value = ResultState.Loading
            _events.value = repository.makeApiCall()
        }
    }
}