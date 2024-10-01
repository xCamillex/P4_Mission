package com.aura.model.transfer

data class TransferRequest(
    val sender: String,
    val recipient: String,
    val amount: Double
)
