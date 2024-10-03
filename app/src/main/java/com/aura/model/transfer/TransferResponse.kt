package com.aura.model.transfer

/**
 * Classe de données représentant la réponse à une requête de transfert d'argent.
 * @param result Indique si le transfert a été effectué avec succès (true) ou non (false).
 */
data class TransferResponse(
    val result: Boolean
)
