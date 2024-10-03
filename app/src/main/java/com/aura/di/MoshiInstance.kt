package com.aura.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * Instance de Moshi configurée pour la sérialisation et la désérialisation JSON.
 * Cet objet fournit une instance de Moshi avec un adaptateur Kotlin pour gérer les classes de données Kotlin.
 */
object MoshiInstance {
    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
}
