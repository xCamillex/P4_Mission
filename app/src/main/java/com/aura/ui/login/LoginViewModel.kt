package com.aura.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.data.login.ConnectionState
import com.aura.data.login.LoginRequest
import com.aura.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository): ViewModel()  {
    private val _state = MutableStateFlow(ConnectionState.INITIAL)
    val state: StateFlow<ConnectionState> = _state
    fun checkFields(identifier: String, password: String) {
        if (identifier.isNotEmpty() && password.isNotEmpty()) {
            //champ de saisie Ok !
            _state.value = ConnectionState.FIELDS_FILLED
        } else {
            //champ de saisie manquant
            _state.value = ConnectionState.INITIAL
        }
    }
    fun Login(identifier: String, password: String) {
        viewModelScope.launch {
            _state.value = ConnectionState.CONNECTING
            try {
                val loginResponse = loginRepository.login(LoginRequest(identifier, password))
                // Check the 'success' property in the response body (assuming your API uses it)
                if (loginResponse.granted) { // Or whatever indicates success in your API response
                    _state.value = ConnectionState.CONNECTED
                    Log.d("connectMMM", "Connexion Ok")
                } else {
                    _state.value = ConnectionState.FAILED
                    Log.d("connectMMM", "Authentication failed")
                }

            } catch (e: Exception) {
                _state.value = ConnectionState.ERROR
                Log.d("connectMMM", "Network error: ${e.message}")
            }
        }
    }
}

