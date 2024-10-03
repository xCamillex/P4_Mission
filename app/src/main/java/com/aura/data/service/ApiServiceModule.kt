package com.aura.data.service

import com.aura.di.AppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/**
 * Module Hilt pour fournir une instance d'ApiService.
 * Ce module est installé dans le composant Singleton, ce qui signifie que l'instance fournie
 * sera partagée à travers toute l'application.
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    /**
     * Fournit une instance d'ApiService.
     * @return L'instance d'ApiService configurée dans l'application.
     */
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return AppConfig.api
    }
}
