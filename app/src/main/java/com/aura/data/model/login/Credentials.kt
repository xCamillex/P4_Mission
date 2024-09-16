package com.aura.data.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the credentials required to log in to the application.
 * @property id The user's ID.
 * @property password The user's password.
 */
/**
 * Représente les informations d'identification requises pour se connecter à l'application.
 * @property id L'ID de l'utilisateur.
 * @property password Le mot de passe de l'utilisateur.
 */
@Serializable
data class Credentials(
    @SerialName("id") val id: String,
    @SerialName("password") val password: String
)
