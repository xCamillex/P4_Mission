package com.aura.data.repository

import com.aura.data.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Module Hilt pour fournir des instances de AuraRepository.
 * Ce module est installé dans le composant ViewModel, ce qui signifie que les instances fournies
 * seront liées à la durée de vie du ViewModel.
 */
@Module
@InstallIn(ViewModelComponent::class)
object AuraRepositoryModule {

    /**
     * Fournit une instance de AuraRepository en utilisant ApiService.
     * @param apiService L'instance d'ApiService utilisée pour créer l'implémentation du dépôt.
     * @return Une instance de AuraRepositoryImpl.
     */
    @Provides
    @ViewModelScoped
    fun bindLoginRepository(apiService: ApiService): AuraRepository {
        return AuraRepositoryImpl(apiService)
    }
}
