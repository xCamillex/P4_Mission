package com.aura.model.login

enum class ConnectionState {
    INITIAL,
    FIELDS_FILLED, // Tous les champs sont remplis
    ERROR, // Erreur lors de la connexion
    FAILED, // Connexion echouee, mauvais ID ou Mot de passe
    CONNECTING, // Connexion en cours
    CONNECTED, // Connexion reussie

}