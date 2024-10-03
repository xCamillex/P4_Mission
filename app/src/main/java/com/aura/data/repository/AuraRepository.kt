package com.aura.data.repository

import com.aura.model.home.AccountRequest
import com.aura.model.home.AccountResponse
import com.aura.model.login.LoginRequest
import com.aura.model.login.LoginResponse
import com.aura.model.transfer.TransferRequest
import com.aura.model.transfer.TransferResponse
import retrofit2.Response

interface AuraRepository {
    suspend fun login(request: LoginRequest): Response<LoginResponse>
    suspend fun accounts(request: AccountRequest): Response<List<AccountResponse>>
    suspend fun transfer(request: TransferRequest): Response<TransferResponse>
}
