package com.aura.data.repository

import com.aura.model.login.LoginRequest
import com.aura.model.login.LoginResponse
import com.aura.data.service.ApiService
import com.aura.model.home.AccountRequest
import com.aura.model.home.AccountResponse
import com.aura.model.transfer.TransferRequest
import com.aura.model.transfer.TransferResponse
import retrofit2.Response

/**
 * Implémentation de l'interface AuraRepository, fournissant des méthodes concrètes
 * pour interagir avec le service API.
 * Cette classe utilise l'instance de ApiService pour effectuer des opérations
 * telles que la connexion, la récupération des comptes et le transfert d'argent.
 *
 * @param apiService L'instance de ApiService utilisée pour effectuer les appels réseau.
 */
class AuraRepositoryImpl(private val apiService: ApiService) : AuraRepository {

    /**
     * Effectue une tentative de connexion en utilisant les informations fournies.
     * @param request Les informations de connexion, y compris l'identifiant et le mot de passe.
     * @return Une réponse contenant le résultat de la tentative de connexion.
     */
    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return apiService.login(request)
    }

    /**
     * Récupère la liste des comptes associés à un utilisateur spécifique.
     * @param request Les informations du compte, y compris l'identifiant de l'utilisateur.
     * @return Une réponse contenant la liste des comptes.
     */
    override suspend fun accounts(request: AccountRequest): Response<List<AccountResponse>> {
        return apiService.accounts(request.id)
    }

    /**
     * Effectue un transfert d'argent entre deux comptes.
     * @param request Les informations du transfert, y compris les identifiants des comptes source et destination.
     * @return Une réponse contenant le résultat du transfert.
     */
    override suspend fun transfer(request: TransferRequest): Response<TransferResponse> {
        return apiService.transfer(request)
    }
}