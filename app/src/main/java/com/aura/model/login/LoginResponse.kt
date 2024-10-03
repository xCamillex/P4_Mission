package com.aura.model.login

/**
 * Classe de données représentant la réponse à une requête de connexion.
 * @param granted Indique si l'accès est accordé (true) ou refusé (false).
 */
data class LoginResponse(
    val granted: Boolean)
