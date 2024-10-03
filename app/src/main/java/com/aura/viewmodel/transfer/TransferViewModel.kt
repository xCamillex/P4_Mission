package com.aura.viewmodel.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.data.repository.AuraRepository
import com.aura.model.transfer.TransferRequest
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
 * ViewModel pour l'écran de transfert d'argent.
 * Ce ViewModel gère l'état de l'interface utilisateur et les événements de navigation liés au transfert d'argent.
 * Il utilise un dépôt (AuraRepository) pour effectuer des opérations réseau et stocker l'état du transfert.
 *
 * @param repository L'instance de AuraRepository utilisée pour interagir avec l'API.
 */
@HiltViewModel
class TransferViewModel @Inject constructor(private val repository: AuraRepository) : ViewModel() {
    // État de l'interface utilisateur pour l'écran de transfert
    private val _uiState = MutableStateFlow(TransferUiState())
    val uiState: StateFlow<TransferUiState> = _uiState.asStateFlow()

    // Canal pour gérer les événements de navigation
    private val _navigationEvents = Channel<NavigationEvent>(
        Channel.CONFLATED
    )
    val navigationEvents = _navigationEvents.receiveAsFlow()

    /**
     * Met à jour l'état lorsque le destinataire change.
     * @param recipient Le nouvel identifiant du destinataire.
     */
    fun onRecipientChanged(recipient: String) {
        _uiState.update { currentState ->
            currentState.copy(
                recipient = recipient,
                isTransferButtonEnabled = recipient.isNotEmpty() && currentState.amount > 0.0
            )
        }
    }

    /**
     * Met à jour l'état lorsque le montant change.
     * @param amount Le nouveau montant à transférer.
     */
    fun onAmountChanged(amount: Double) {
        _uiState.update { currentState ->
            currentState.copy(
                amount = amount,
                isTransferButtonEnabled = amount > 0.0 && currentState.recipient.isNotEmpty()
            )

        }
        _uiState.value = _uiState.value.copy(amount = amount)
    }

    /**
     * Gère les erreurs en mettant à jour l'état avec un message d'erreur.
     * @param message Le message d'erreur à afficher.
     */
    private fun onError(message: String) {
        _uiState.update { currentState ->
            currentState.copy(
                error = message,
                isLoading = false,
                isTransferButtonEnabled = true
            )
        }
    }

    /**
     * Réinitialise le message d'erreur.
     */
    fun resetError() {
        _uiState.update { currentState -> currentState.copy(error = "") }
    }

    /**
     * Gère le clic sur le bouton de transfert.
     * Effectue une validation des données et appelle le dépôt pour exécuter le transfert.
     * @param sender L'identifiant de l'expéditeur.
     * @param recipient L'identifiant du destinataire.
     * @param amount Le montant à transférer.
     */
    fun onTransferClicked(sender: String, recipient: String, amount: Double) {
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(isLoading = true, error = "") }
            if (amount <= 0.0) {
                onError("Le montant doit être supérieur à 0")

            } else if (recipient.isEmpty()) {
                onError("Le destinataire ne peut pas être vide")

            } else {
                // Appeler le repository pour effectuer le transfert
                val response = repository.transfer(
                    TransferRequest(
                        recipient = recipient,
                        amount = amount,
                        sender = sender
                    )
                )
                if (response.isSuccessful) {
                    if (response.body()?.result == true) {
                        _uiState.update { currentState -> currentState.copy(isLoading = false) }
                        _navigationEvents.send(NavigationEvent.NavigateToHome)
                    } else {
                        onError("balance insuffisante")
                    }

                } else {
                    onError("Erreur lors du transfert")
                }
            }
        }
    }
}