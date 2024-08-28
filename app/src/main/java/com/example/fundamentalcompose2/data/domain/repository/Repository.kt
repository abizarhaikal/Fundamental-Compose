package com.example.fundamentalcompose2.data.domain.repository

import com.example.fundamentalcompose2.ResultState
import com.example.fundamentalcompose2.data.remote.ApiService
import com.example.fundamentalcompose2.data.response.ResponseEvents

interface Repository {
    suspend fun makeApiCall() : ResultState<ResponseEvents>
    suspend fun getAllEvents() : ResultState<ResponseEvents>
    suspend fun searchEvents(keyword: String) : ResultState<ResponseEvents>
}