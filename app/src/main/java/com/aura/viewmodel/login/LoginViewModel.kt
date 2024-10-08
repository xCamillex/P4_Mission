package com.aura.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.data.repository.AuraRepository
import com.aura.model.login.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel pour l'écran de connexion.
 * Ce ViewModel gère l'état de l'interface utilisateur et les événements de navigation liés à la connexion de l'utilisateur.
 * Il utilise un dépôt (AuraRepository) pour effectuer des opérations de connexion et gérer les erreurs.
 *
 * @param repository L'instance de AuraRepository utilisée pour interagir avec l'API.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuraRepository) : ViewModel() {
    // État de l'interface utilisateur pour l'écran de connexion
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Canal pour gérer les événements de navigation
    private val _navigationEvent = Channel<NavigationEvent>(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    /**
     * Met à jour l'état lorsque l'identifiant change.
     * @param identifier Le nouvel identifiant de l'utilisateur.
     */
    fun onIdentifierChanged(identifier: String) {
        _uiState.update { currentState ->
            currentState.copy(
                identifier = identifier,
                isLoginButtonEnabled = identifier.isNotEmpty() && currentState.password.isNotEmpty()
            )
        }
    }

    /**
     * Met à jour l'état lorsque le mot de passe change.
     * @param password Le nouveau mot de passe de l'utilisateur.
     */
    fun onPasswordChanged(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                isLoginButtonEnabled = password.isNotEmpty() && currentState.identifier.isNotEmpty()
            )
        }
    }

    /**
     * Gère le clic sur le bouton de connexion.
     * Effectue une validation des données et appelle le dépôt pour exécuter la connexion.
     */
    fun onLoginClicked() {
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(isLoading = true, showErrorMessage = false, showRetryButton = false) }

            // Appeler le repository pour effectuer la connexion
            val response = try {
                repository.login(LoginRequest(_uiState.value.identifier, _uiState.value.password))
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = "Erreur de connexion : ${e.localizedMessage}",
                        showErrorMessage = true,
                        showRetryButton = true
                    )
                }
                return@launch
            }

            if (response.isSuccessful && response.body()?.granted == true) {
                _navigationEvent.send(NavigationEvent.ShowSnackbar("Authentification réussie!"))
                _navigationEvent.send(NavigationEvent.NavigateToHome)
            } else {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = "Identifiants incorrects",
                        showErrorMessage = true,
                        showRetryButton = true
                    )
                }
            }

            _uiState.update { currentState -> currentState.copy(isLoading = false) }
        }
    }

    /**
     * Gère le clic sur le bouton de réessai.
     * Réessaie la connexion en appelant la méthode onLoginClicked.
     */
    fun onRetryClicked() {
        onLoginClicked() // Réessayer la connexion
    }
}


