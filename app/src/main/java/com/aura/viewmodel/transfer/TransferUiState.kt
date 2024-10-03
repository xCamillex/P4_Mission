package com.aura.viewmodel.transfer

/**
 * Classe de données représentant l'état de l'interface utilisateur pour l'écran de transfert.
 * Cette classe contient des propriétés pour gérer l'état de chargement, la validité du bouton de transfert,
 * les messages d'erreur, ainsi que les informations sur le destinataire et le montant à transférer.
 *
 * @param isLoading Indique si l'interface est en cours de chargement.
 * @param isTransferButtonEnabled Indique si le bouton de transfert est activé.
 * @param error Message d'erreur à afficher, s'il y en a un.
 * @param recipient L'identifiant du destinataire du transfert.
 * @param amount Le montant à transférer.
 */
data class TransferUiState(
    val isLoading: Boolean = false,
    val isTransferButtonEnabled : Boolean = false,
    val error: String = "",
    val recipient: String = "",
    val amount: Double = 0.0,
)
