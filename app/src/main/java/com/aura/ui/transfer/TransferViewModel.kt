package com.aura.ui.transfer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine


class TransferViewModel : ViewModel() {
    private val _recipient = MutableStateFlow("")
    val recipient: StateFlow<String> = _recipient

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount

    val isTransferEnabled: Flow<Boolean> = combine(recipient, amount) { recipient, amount ->
        recipient.isNotBlank() && amount.isNotBlank()
    }

    fun updateRecipient(newRecipient: String) {
        _recipient.value = newRecipient
    }

    fun updateAmount(newAmount: String) {
        _amount.value = newAmount
    }
}
