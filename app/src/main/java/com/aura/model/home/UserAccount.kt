package com.aura.model.home

import com.squareup.moshi.Json

/**
 * Représente un compte.
 * @property id L'ID du compte.
 * @property main Indique si le compte est le compte principal de l'utilisateur.
 * @property balance Le solde du compte.
 */
data class UserAccount (
   @Json(name = "id") val id: String,
    @Json(name = "main") val main: Boolean,
    @Json(name = "balance") val balance: Double)
