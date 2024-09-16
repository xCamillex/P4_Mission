package com.aura.data.service

import com.aura.data.model.Account
import com.aura.data.model.login.Credentials
import com.aura.data.model.login.CredentialsResult
import com.aura.data.model.transfer.Transfer
import com.aura.data.model.transfer.TransferResult

/**
 * An interface for interacting with the API.
 * Une interface pour interagir avec l'API.
 */
interface ApiService {

    /**
     * Logs in a user.
     * @param credentials The user's credentials.
     * @return The user's account information, or an error if the login failed.
     */
    /**
     * Connecte un utilisateur.
     * @param credentials Les informations d'identification de l'utilisateur.
     * @return Les informations de compte de l'utilisateur ou une erreur si la connexion a échoué.
     */
    fun login(credentials: Credentials): CredentialsResult

    /**
     * Fetches a user's accounts information.
     * @param id The user's ID.
     * @return A list of the user's accounts.
     */
    /**
     * Récupère les informations des comptes d'un utilisateur.
     * @param id L'ID de l'utilisateur.
     * @return Une liste des comptes de l'utilisateur.
     */
    fun accounts(id: String): List<Account>

    /**
     * Transfers money between accounts.
     * @param transfer The transfer details.
     * @return The result of the transfer, or an error if the transfer failed.
     */
    /**
     * Transfère de l'argent entre des comptes.
     * @param transfer Les détails du transfert.
     * @return Le résultat du transfert, ou une erreur si le transfert a échoué.
     */
    fun transfer(transfer: Transfer): TransferResult

}