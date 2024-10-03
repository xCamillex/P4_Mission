package com.aura.data.service

import com.aura.model.home.AccountResponse
import com.aura.model.login.LoginRequest
import com.aura.model.login.LoginResponse
import com.aura.model.transfer.TransferRequest
import com.aura.model.transfer.TransferResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
/**
 * Interface définissant les opérations API pour l'application.
 * Cette interface utilise Retrofit pour décrire les appels API liés à l'authentification,
 * à la récupération des comptes et aux transferts d'argent.
 */
interface ApiService {

    /**
     * Effectue une tentative de connexion avec les identifiants fournis.
     * @param request Les informations de connexion, y compris l'identifiant et le mot de passe.
     * @return Une réponse contenant le résultat de la tentative de connexion.
     */
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    /**
     * Récupère la liste des comptes associés à l'utilisateur actuellement connecté.
     * @param id L'identifiant unique de l'utilisateur.
     * @return Une réponse contenant la liste des comptes.
     */
    @GET("/accounts/{id}")
    suspend fun accounts(@Path("id") id: String): Response<List<AccountResponse>>

    /**
     * Effectue un transfert d'argent entre deux comptes.
     * @param request Les informations du transfert, y compris l'expéditeur, le destinataire et le montant.
     * @return Une réponse indiquant le résultat du transfert.
     */
    @POST("/transfer")
    suspend fun transfer(@Body request: TransferRequest): Response<TransferResponse>
}