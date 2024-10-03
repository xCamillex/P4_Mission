package com.aura.data.service

import com.aura.model.home.AccountResponse
import com.aura.model.login.LoginRequest
import com.aura.model.login.LoginResponse
import com.aura.model.transfer.TransferRequest
import com.aura.model.transfer.TransferResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/accounts/{id}")
    suspend fun accounts(@Path("id") id: String): Response<List<AccountResponse>>

    @POST("/transfer")
    suspend fun transfer(@Body request: TransferRequest): Response<TransferResponse>
}