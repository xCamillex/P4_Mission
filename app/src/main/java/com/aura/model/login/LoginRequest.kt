package com.aura.model.login

/**
 * Classe de données représentant une requête de connexion.
 * @param id L'identifiant de l'utilisateur.
 * @param password Le mot de passe de l'utilisateur.
 */
data class LoginRequest(
    val id: String,
    val password: String)