package com.aura.data.model

/**
 * Represents a user.
 * @property id The user's ID.
 * @property firstname The user's first name.
 * @property lastname The user's last name.
 * @property password The user's password.
 * @property accounts A list of the user's accounts.
 */
/**
 * Représente un utilisateur.
 * @property id L'ID de l'utilisateur.
 * @property firstname Le prénom de l'utilisateur.
 * @property lastname Le nom de famille de l'utilisateur.
 * @property password Le mot de passe de l'utilisateur.
 * @property accounts Une liste des comptes de l'utilisateur.
 */
data class User(
    val id: String,
    val firstname: String,
    val lastname: String,
    val password: String,
    val accounts: List<Account>,
)
