package com.aura.model.home

sealed class AccountsState {
    object Loading : AccountsState()
    data class Success(val userAccount: UserAccount) : AccountsState()
    data class Error(val message: String, val showRetryButton: Boolean = true) : AccountsState()



}