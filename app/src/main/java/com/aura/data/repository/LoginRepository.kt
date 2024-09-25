package com.aura.data.repository

import com.aura.data.login.LoginRequest
import com.aura.data.login.LoginResponse
import com.aura.data.service.ApiService
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return apiService.login(loginRequest)
    }
}