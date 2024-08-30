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
class PastViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _past = MutableLiveData<ResultState<ResponseEvents>>()
    val past: LiveData<ResultState<ResponseEvents>> = _past

    fun fetchPast() {
        viewModelScope.launch {
            _past.value = ResultState.Loading
            _past.value = repository.getEventPast()
        }
    }
}