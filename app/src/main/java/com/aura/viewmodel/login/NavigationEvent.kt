package com.aura.viewmodel.login

/**
 * Classe scellée représentant les événements de navigation dans l'application.
 * Les classes scellées sont utilisées pour définir un ensemble limité d'options
 * et sont utiles pour gérer la navigation dans l'architecture MVVM.
 */
sealed class NavigationEvent {
    /**
     * Événement de navigation pour aller à l'écran d'accueil.
     */
    object NavigateToHome : NavigationEvent()

    /**
     * Événement pour afficher un message de succès et naviguer vers une autre destination.
     * @param message Le message de succès à afficher.
     */
    data class ShowSuccessAndNavigate(val message: String) : NavigationEvent()

    /**
     * Événement pour afficher un Snackbar avec un message.
     * @param message Le message à afficher dans le Snackbar.
     */
    data class ShowSnackbar(val message: String) : NavigationEvent()
}