package com.example.fundamentalcompose2.data.remote

import com.example.fundamentalcompose2.data.response.ResponseDetail
import com.example.fundamentalcompose2.data.response.ResponseEvents
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("events/{id}")
    suspend fun getEventDetail(
        @Path("id") id: Int
    ): Response<ResponseDetail>

    @GET("events?active=-1&limit=1")
    suspend fun getEventRandom(): Response<ResponseEvents>

    @GET("events?active=0")
    suspend fun getEventPast(): Response<ResponseEvents>
}