package com.aura.data.repository

import com.aura.model.home.AccountRequest
import com.aura.model.home.AccountResponse
import com.aura.model.login.LoginRequest
import com.aura.model.login.LoginResponse
import com.aura.model.transfer.TransferRequest
import com.aura.model.transfer.TransferResponse
import retrofit2.Response

/**
 * Interface définissant les méthodes pour interagir avec les opérations liées à l'authentification et aux comptes.
 * Cette interface définit les méthodes pour effectuer des opérations telles que la connexion,
 * la récupération des comptes et le transfert d'argent. Chaque méthode utilise des requêtes et
 * retourne des réponses encapsulées dans des objets Response.
 */
interface AuraRepository {
    /**
     * Effectue une tentative de connexion avec les identifiants fournis.
     * @param request Les informations de connexion, y compris l'identifiant et le mot de passe.
     * @return Une réponse contenant le résultat de la tentative de connexion.
     */
    suspend fun login(request: LoginRequest): Response<LoginResponse>

    /**
     * Récupère la liste des comptes associés à un utilisateur.
     * @param request Les informations nécessaires pour identifier l'utilisateur.
     * @return Une réponse contenant une liste des comptes de l'utilisateur.
     */
    suspend fun accounts(request: AccountRequest): Response<List<AccountResponse>>

    /**
     * Effectue un transfert d'argent entre deux comptes.
     * @param request Les informations du transfert, y compris l'expéditeur, le destinataire et le montant.
     * @return Une réponse indiquant le résultat du transfert.
     */
    suspend fun transfer(request: TransferRequest): Response<TransferResponse>
}
