package com.aura.data.model.transfer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a transfer of money between two accounts.
 * @property senderId The ID of the sender account.
 * @property recipientId The ID of the recipient account.
 * @property amount The amount of money to transfer.
 */
/**
 * Représente un transfert d'argent entre deux comptes.
 * @property senderId L'ID du compte expéditeur.
 * @property recipientId L'ID du compte destinataire.
 * @property amount Le montant d'argent à transférer.
 */
@Serializable
data class Transfer(
    @SerialName("sender") val senderId: String,
    @SerialName("recipient") val recipientId: String,
    @SerialName("amount") val amount: Double
)
