package com.aura.data.repository

import com.aura.model.login.LoginRequest
import com.aura.model.login.LoginResponse
import com.aura.data.service.ApiService
import com.aura.model.home.UserAccount
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import javax.inject.Inject


class AuraRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return apiService.login(loginRequest)
    }
    suspend fun getUserAccounts(userId: String): Response<List<UserAccount>> {
        return apiService.getUserAccounts(userId)
    }
}