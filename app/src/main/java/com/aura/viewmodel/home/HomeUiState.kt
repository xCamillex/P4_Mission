package com.aura.viewmodel.home

/**
 * Classe de données représentant l'état de l'interface utilisateur pour l'écran d'accueil.
 * Cette classe contient des propriétés pour gérer l'état de chargement, les messages d'erreur
 * et l'affichage d'un bouton de réessai.
 *
 * @param isLoading Indique si l'interface est en cours de chargement.
 * @param showRetryButton Indique si le bouton de réessai doit être affiché en cas d'erreur.
 * @param error Message d'erreur à afficher, s'il y en a un.
 */
data class HomeUiState (
    val isLoading: Boolean = false,
    val showRetryButton: Boolean = false,
    val error: String = "",
)