package com.aura.data.repository

import com.aura.model.login.LoginRequest
import com.aura.model.login.LoginResponse
import com.aura.data.service.ApiService
import com.aura.model.home.AccountRequest
import com.aura.model.home.AccountResponse
import com.aura.model.transfer.TransferRequest
import com.aura.model.transfer.TransferResponse
import retrofit2.Response

class AuraRepositoryImpl(private val apiService: ApiService) : AuraRepository {
    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return apiService.login(request)
    }

    override suspend fun accounts(request: AccountRequest): Response<List<AccountResponse>> {
        return apiService.accounts(request.id)
    }

    override suspend fun transfer(request: TransferRequest): Response<TransferResponse> {
        return apiService.transfer(request)
    }
}