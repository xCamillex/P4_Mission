package com.aura.di

import com.aura.data.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object AppConfig {
    private const val BASE_URL = "http://10.0.2.2:8080"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(MoshiInstance.moshi))
            .build()
            .create(ApiService::class.java)
    }
}