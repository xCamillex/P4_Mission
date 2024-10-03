package com.aura.data.repository

import com.aura.data.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuraRepositoryModule {
    @Provides
    @ViewModelScoped
    fun bindLoginRepository(apiService: ApiService): AuraRepository {
        return AuraRepositoryImpl(apiService)
    }
}
