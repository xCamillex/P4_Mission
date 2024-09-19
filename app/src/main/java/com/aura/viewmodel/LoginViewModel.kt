package com.aura.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    // MutableStateFlows privés pour stocker les valeurs d'entrée du nom d'utilisateur et du mot de passe
    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    // MutableStateFlow privé pour suivre l'état du bouton de connexion (activé/désactivé)
    private val _isLoginButtonEnabled = MutableStateFlow(false)

    // MutableStateFlow privé pour suivre l'état de chargement
    private val _isLoading = MutableStateFlow(false)

    // Propriétés publiques pour accéder aux valeurs d'entrée et à l'état du bouton de connexion
    val username: StateFlow<String> get() = _username.asStateFlow()
    val password: StateFlow<String> get() = _password.asStateFlow()
    val isLoginButtonEnabled: StateFlow<Boolean> get() = _isLoginButtonEnabled.asStateFlow()
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    init {
        // Observer les modifications du nom d'utilisateur et du mot de passe
        viewModelScope.launch {
            // Combinez les dernières valeurs du nom d'utilisateur et du mot de passe et vérifiez si les deux ne sont pas vides
            combine(_username, _password) { username, password ->
                username.isNotEmpty() && password.isNotEmpty()
                // Collecter le résultat et mettre à jour l'état du bouton de connexion (activé si les deux champs ne sont pas vides)
            }.collect { isEnabled ->
                _isLoginButtonEnabled.value = isEnabled
            }
        }
    }

    // Méthode pour mettre à jour le champ d'identifiant
    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername.trim()
    }

    // Méthode pour mettre à jour le champ de mot de passe
    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword.trim()
    }

    // Événement pour la navigation
    private val _navigateToHomeEvent = MutableSharedFlow<Unit>()
    val navigateToHomeEvent: SharedFlow<Unit> get() = _navigateToHomeEvent

    // Méthode pour déclencher l'événement de navigation
    fun navigateToHome() {
        viewModelScope.launch {
            _isLoading.value = true // Démarrer le chargement
            _navigateToHomeEvent.emit(Unit) // Déclencher l'événement de navigation
            _isLoading.value = false // Arrêter le chargement
        }
    }
}