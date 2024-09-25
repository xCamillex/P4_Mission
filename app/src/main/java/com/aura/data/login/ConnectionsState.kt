package com.aura.data.login

enum class ConnectionState {
    INITIAL,
    FIELDS_FILLED, // Tous les champs sont remplis
    ERROR, // Erreur lors de la connexion
    FAILED, // Connexion echouee
    CONNECTING, // Connexion en cours
    CONNECTED, // Connexion reussie

}