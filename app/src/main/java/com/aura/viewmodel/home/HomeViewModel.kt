package com.aura.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.data.repository.AuraRepository
import com.aura.model.home.AccountRequest
import com.aura.model.home.AccountResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel pour l'écran d'accueil.
 * Ce ViewModel gère l'état de l'interface utilisateur et les opérations liées à la récupération des comptes d'un utilisateur.
 * Il utilise un dépôt (AuraRepository) pour effectuer des appels API afin de récupérer les informations de compte.
 *
 * @param repository L'instance de AuraRepository utilisée pour interagir avec l'API.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: AuraRepository): ViewModel() {
    companion object {
        private const val TAG = "HomeViewModel" // Tag pour le logging et le débogage
    }

    // État de l'interface utilisateur pour l'écran d'accueil
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // Liste des comptes récupérés, encapsulée dans LiveData
    private val _accounts = MutableLiveData<List<AccountResponse>>()
    val accounts: LiveData<List<AccountResponse>> = _accounts

    /**
     * Récupère les comptes de l'utilisateur en appelant le dépôt.
     * Met à jour l'état de l'interface utilisateur en fonction du succès ou de l'échec de l'appel.
     *
     * @param login Identifiant de l'utilisateur pour récupérer les comptes.
     */
    fun getAccounts(login: String) {
        viewModelScope.launch {
            // Met à jour l'état pour indiquer le chargement
            _uiState.update { currentState -> currentState.copy(isLoading = true, showRetryButton = false) }
            try {
                val response = repository.accounts(AccountRequest(id = login))
                if (response.isSuccessful) {
                    _accounts.value = response.body() // Met à jour la liste des comptes
                    _uiState.update { currentState -> currentState.copy(isLoading = false) }
                } else {
                    // Gère les réponses non réussies
                    _uiState.update { currentState -> currentState.copy(isLoading = false, error = "Impossible de contacter le serveur", showRetryButton = true) }
                }

            } catch (e: Exception) {
                // Gère les exceptions lors de l'appel API
                _uiState.update { currentState -> currentState.copy(isLoading = false, showRetryButton = true) }
            }
        }
    }

    /**
     * Réinitialise l'erreur dans l'état de l'interface utilisateur.
     */
    fun resetError() {
        _uiState.update { currentState -> currentState.copy(error = "") }
    }

    /**
     * Réinitialise l'état du bouton de réessai.
     */
    fun resetRetry() {
        _uiState.update { currentState -> currentState.copy(showRetryButton = false) }
    }
}