package com.example.fundamentalcompose2.data.domain

import com.example.fundamentalcompose2.ResultState
import com.example.fundamentalcompose2.data.domain.repository.Repository
import com.example.fundamentalcompose2.data.remote.ApiService
import com.example.fundamentalcompose2.data.response.ResponseEvents

class RepositoryImpl(
    private val api: ApiService
) : Repository {
    override suspend fun makeApiCall(): ResultState<ResponseEvents> {
        return try {
            val response = api.getEvents()
            if (response.isSuccessful && response.body() != null) {
                ResultState.Success(response.body()!!)
            } else {
                ResultState.Error(response.body()?.error.toString())
            }
        } catch (e: Exception) {
            ResultState.Error(e.message.toString())
        }
    }

    override suspend fun getAllEvents(): ResultState<ResponseEvents> {
        return try {
            val responseAllEvents = api.getAllEvents()
            if (responseAllEvents.isSuccessful && responseAllEvents.body() != null) {
                ResultState.Success(responseAllEvents.body()!!)
            }else {
                ResultState.Error(responseAllEvents.body()?.error.toString())
            }
        } catch (e: Exception) {
            ResultState.Error(e.message.toString())
        }
    }

    override suspend fun searchEvents(keyword: String): ResultState<ResponseEvents> {
        return try {
            val responseSearchEvents = api.searchEvents(keyword = keyword)
            if (responseSearchEvents.isSuccessful && responseSearchEvents.body() != null) {
                ResultState.Success(responseSearchEvents.body()!!)
            } else {
                ResultState.Error(responseSearchEvents.body()?.error.toString())
            }
        } catch (e: Exception) {
            ResultState.Error(e.message.toString())
        }
    }

}
