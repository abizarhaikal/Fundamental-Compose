package com.example.fundamentalcompose2.data.domain

import com.example.fundamentalcompose2.ResultState
import com.example.fundamentalcompose2.data.domain.repository.Repository
import com.example.fundamentalcompose2.data.remote.ApiService
import com.example.fundamentalcompose2.data.response.ResponseDetail
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
            } else {
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

    override suspend fun getEventDetail(id: Int): ResultState<ResponseDetail> {
        return try {
            val responseDetail = api.getEventDetail(id = id)
            if (responseDetail.isSuccessful && responseDetail.body() != null) {
                ResultState.Success(responseDetail.body()!!)
            } else {
                ResultState.Error(responseDetail.body()?.error.toString())
            }
        } catch (e: Exception) {
            ResultState.Error(e.message.toString())
        }
    }

    override suspend fun getEventRandom(): ResultState<ResponseEvents> {
        return try {
            val responseNotification = api.getEventRandom()
            if (responseNotification.isSuccessful && responseNotification.body() != null) {
                ResultState.Success(responseNotification.body()!!)
            } else {
                ResultState.Error(responseNotification.body()?.error.toString())
            }
        } catch (e: Exception) {
            ResultState.Error(e.message.toString())
        }
    }

    override suspend fun getEventPast(): ResultState<ResponseEvents> {
        return try {
            val responsePast = api.getEventPast()
            if (responsePast.isSuccessful && responsePast.body() != null) {
                ResultState.Success(responsePast.body()!!)
            } else {
                ResultState.Error(responsePast.body()?.error.toString())
            }
        } catch (e: Exception) {
            ResultState.Error(e.message.toString())
        }
    }
}
