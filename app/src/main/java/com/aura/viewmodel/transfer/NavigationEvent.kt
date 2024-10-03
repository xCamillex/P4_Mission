package com.aura.viewmodel.transfer

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
}