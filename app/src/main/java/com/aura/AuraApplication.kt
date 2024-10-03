package com.aura

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe d'application de l'application Android utilisant Hilt pour l'injection de dépendances.
 * L'annotation `@HiltAndroidApp` initialise Hilt et génère le code nécessaire pour la configuration de l'injection de dépendances.
 * Cette classe doit être déclarée dans le fichier AndroidManifest.xml pour permettre l'utilisation de Hilt dans l'application.
 */
@HiltAndroidApp
class AuraApplication : Application() {
}