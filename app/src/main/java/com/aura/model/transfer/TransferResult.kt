package com.aura.model.transfer

sealed class TransferResult {
    data class Success(val message: String): TransferResult()
    data class Error(val message: String): TransferResult()
}