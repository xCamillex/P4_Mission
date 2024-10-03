package com.aura.model.home

import com.squareup.moshi.Json

/**
 * Classe de données représentant la réponse contenant les informations d'un compte.
 * @param id L'identifiant unique du compte.
 * @param main Indique si ce compte est le compte principal (true) ou non (false).
 * @param balance Le solde actuel du compte.
 */
data class AccountResponse(
    val id: String,
    val main: Boolean,
    val balance: Double
)