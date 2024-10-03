package com.aura.model.home

import com.squareup.moshi.Json

/**
 * Représente un compte.
 * @property id L'ID du compte.
 * @property main Indique si le compte est le compte principal de l'utilisateur.
 * @property balance Le solde du compte.
 */
data class AccountResponse(
    val id: String,
    val main: Boolean,
    val balance: Double
)