package com.example.fundamentalcompose2.di

import com.example.fundamentalcompose2.data.domain.RepositoryImpl
import com.example.fundamentalcompose2.data.domain.repository.Repository
import com.example.fundamentalcompose2.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Injection {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://event-api.dicoding.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService): Repository {
        return RepositoryImpl(apiService)
    }

}