package com.example.fundamentalcompose2.data.domain.repository

import com.example.fundamentalcompose2.ResultState
import com.example.fundamentalcompose2.data.remote.ApiService
import com.example.fundamentalcompose2.data.response.ResponseDetail
import com.example.fundamentalcompose2.data.response.ResponseEvents

interface Repository {
    suspend fun makeApiCall() : ResultState<ResponseEvents>
    suspend fun getAllEvents() : ResultState<ResponseEvents>
    suspend fun searchEvents(keyword: String) : ResultState<ResponseEvents>
    suspend fun getEventDetail(id: Int) : ResultState<ResponseDetail>
    suspend fun getEventRandom() : ResultState<ResponseEvents>
    suspend fun getEventPast() : ResultState<ResponseEvents>
}