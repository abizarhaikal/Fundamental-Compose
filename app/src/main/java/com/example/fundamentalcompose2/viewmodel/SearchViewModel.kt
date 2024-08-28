package com.example.fundamentalcompose2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fundamentalcompose2.ResultState
import com.example.fundamentalcompose2.data.domain.repository.Repository
import com.example.fundamentalcompose2.data.response.ResponseEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _searchEvents = MutableLiveData<ResultState<ResponseEvents>>()
    val searchEvents: LiveData<ResultState<ResponseEvents>> = _searchEvents

    // Fungsi untuk memperbarui teks pencarian
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    // Fungsi untuk melakukan pencarian berdasarkan teks pencarian saat ini
    fun searchEvents() {
        viewModelScope.launch {
            _isSearching.value = true
            _searchEvents.value = ResultState.Loading
            try {
                _searchEvents.value = repository.searchEvents(_searchText.value)
            } catch (e: Exception) {
                _searchEvents.value = ResultState.Error(e.message ?: "Unknown Error")
            } finally {
                _isSearching.value = false
            }
        }
    }
}
