package com.aura.data.model.repository

import com.aura.data.model.Account
import com.aura.data.model.login.Credentials
import com.aura.data.model.login.CredentialsResult
import com.aura.data.model.transfer.Transfer
import com.aura.data.model.transfer.TransferResult
import com.aura.data.service.ApiService
import com.aura.data.service.LocalApiService

/**
 * An object that provides a single point of access to the API.
 * * Un objet qui fournit un point d'accès unique à l'API.
 */
object ApiRepository: ApiService {

    /**
     * The API service.
     */
    private val apiService: ApiService = LocalApiService()

    /**
     * Authentifie un utilisateur en déléguant l'opération au service d'API.
     *
     * @param credentials Objet contenant les identifiants de connexion de l'utilisateur (identifiant, mot de passe).
     * @return Un résultat de type CredentialsResult qui indique si l'authentification a réussi ou échoué.
     */
    override fun login(credentials: Credentials): CredentialsResult {
        return apiService.login(credentials)
    }

    /**
     * Récupère la liste des comptes associés à un identifiant utilisateur spécifique en
     * déléguant l'appel au service d'API.
     *
     * @param id Identifiant unique de l'utilisateur pour lequel les comptes sont recherchés.
     * @return Une liste d'objets Account correspondant aux comptes associés à l'utilisateur.
     */
    override fun accounts(id: String): List<Account> {
        return apiService.accounts(id)
    }

    /**
     * Effectue un transfert de fonds en déléguant l'opération au service d'API.
     *
     * @param transfer Objet contenant les détails du transfert (montant, comptes source et destination).
     * @return Un résultat de type TransferResult indiquant si le transfert a réussi ou échoué.
     */
    override fun transfer(transfer: Transfer): TransferResult {
        return apiService.transfer(transfer)
    }

}