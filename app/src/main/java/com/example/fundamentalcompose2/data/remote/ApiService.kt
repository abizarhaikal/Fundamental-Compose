package com.example.fundamentalcompose2.data.remote

import com.example.fundamentalcompose2.data.response.ListEventsItem
import com.example.fundamentalcompose2.data.response.ResponseEvents
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events?active=1")
    suspend fun getEvents(): Response<ResponseEvents>

    @GET("events?active=-1")
    suspend fun getAllEvents() : Response<ResponseEvents>

    @GET("events?active=-1")
    suspend fun searchEvents(
        @Query("q") keyword: String
    ): Response<ResponseEvents>
}