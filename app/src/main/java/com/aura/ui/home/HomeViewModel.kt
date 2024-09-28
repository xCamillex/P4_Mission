package com.aura.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.data.repository.AuraRepository
import com.aura.model.home.AccountsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for handling logic related to the HomeFragment.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val auraRepository: AuraRepository) : ViewModel() {

    private val _accountsState = MutableStateFlow<AccountsState>(AccountsState.Loading)
    val accountsState: StateFlow<AccountsState> = _accountsState

    fun getUserAccounts(userId: String) {
        viewModelScope.launch {
            _accountsState.value = AccountsState.Loading
            try {
                val response = auraRepository.getUserAccounts(userId)
                delay(1000)
                if (response.isSuccessful) {
                    val accounts = response.body()
                    if (!accounts.isNullOrEmpty()) {
                        // Assuming you want the balance from the "main" account
                        val mainAccount = accounts.find { it.main }
                        if (mainAccount != null) {
                            _accountsState.value = AccountsState.Success(mainAccount)
                        } else {
                            _accountsState.value = AccountsState.Error("No main account found")
                        }
                    } else {
                        _accountsState.value =
                            AccountsState.Error("No accounts found for this user")
                    }
                } else {
                    _accountsState.value = AccountsState.Error("Failed to fetch accounts")
                }
            } catch (e: Exception) {
                _accountsState.value = AccountsState.Error("Network error: ${e.message}")
            }
        }
    }
}
