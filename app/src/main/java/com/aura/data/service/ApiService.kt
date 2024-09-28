package com.aura.data.service

import com.aura.model.home.UserAccount
import com.aura.model.login.LoginRequest
import com.aura.model.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse // Return LoginResponse directly

    @GET("/accounts/{id}")
    suspend fun getUserAccounts(@Path("id") id: String): Response<List<UserAccount>>

}