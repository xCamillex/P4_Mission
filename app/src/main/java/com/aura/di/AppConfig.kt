package com.aura.di

import com.aura.data.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Configuration de l'application contenant les paramètres globaux.
 * Cet objet est responsable de la création et de la configuration de l'instance d'ApiService
 * utilisée pour effectuer les appels API.
 */
object AppConfig {
    private const val BASE_URL = "http://10.0.2.2:8080"

    /**
     * Instance d'ApiService configurée pour les appels réseau.
     * Cette instance est créée de manière paresseuse pour éviter une initialisation inutile
     * jusqu'à ce qu'elle soit réellement utilisée.
     */
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(MoshiInstance.moshi))
            .build()
            .create(ApiService::class.java)
    }
}