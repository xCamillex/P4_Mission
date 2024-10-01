package com.aura.ui.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.data.repository.AuraRepository
import com.aura.model.transfer.TransferRequest
import com.aura.model.transfer.TransferResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(private val auraRepository: AuraRepository): ViewModel() {
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

    fun makeTransfer(sender: String, recipient: String, amount: Double) {
        viewModelScope.launch {
            val result = try {
                val response = auraRepository.makeTransfer(
                    TransferRequest(sender, recipient, amount)
                )
                if (response.success) {
                    TransferResult.Success("Transfer successful!")
                } else {
                    TransferResult.Error(response.message ?: "Transfer failed")
                }
            } catch (e: Exception) {
                TransferResult.Error("Transfer failed: ${e.message}")
            }

            when (result) {
                is TransferResult.Success -> {
                    // Handle success, e.g., show success message, navigate
                }

                is TransferResult.Error -> {
                    // Handle error, e.g., show error message
                }
            }
        }
    }
}
