package com.aura.model.transfer

/**
 * Classe de données représentant une requête de transfert d'argent.
 * @param sender L'identifiant de l'expéditeur du transfert.
 * @param recipient L'identifiant du destinataire du transfert.
 * @param amount Le montant à transférer.
 */
data class TransferRequest(
    val sender: String,
    val recipient: String,
    val amount: Double
)
