package com.aura.viewmodel.login

/**
 * Classe de données représentant l'état de l'interface utilisateur pour l'écran de connexion.
 * Cette classe contient des propriétés pour gérer les informations d'identification de l'utilisateur,
 * l'état de chargement, les messages d'erreur, et la validité du bouton de connexion.
 *
 * @param identifier L'identifiant de l'utilisateur.
 * @param password Le mot de passe de l'utilisateur.
 * @param isLoading Indique si l'interface est en cours de chargement.
 * @param error Message d'erreur à afficher, s'il y en a un.
 * @param isLoginButtonEnabled Indique si le bouton de connexion est activé.
 * @param showErrorMessage Indique si le message d'erreur doit être affiché.
 * @param showRetryButton Indique si le bouton de réessai doit être affiché.
 */
data class LoginUiState (
    val identifier: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val isLoginButtonEnabled: Boolean = false,
    val showErrorMessage: Boolean = false,
    val showRetryButton: Boolean = false
)