package com.aura.data.service

import com.aura.data.model.Account
import com.aura.data.model.User
import com.aura.data.model.login.Credentials
import com.aura.data.model.login.CredentialsResult
import com.aura.data.model.transfer.Transfer
import com.aura.data.model.transfer.TransferResult

/**
 * A local implementation of the ApiService interface.
 * Une implémentation locale de l'interface ApiService.
 */
class LocalApiService : ApiService {

    /**
     * A list of all users.
     * Une liste de tous les utilisateurs.
     */
    private val users = listOf(
        User("1234", "Pierre", "Brisette", "p@sswOrd",
            listOf(
                Account("1", true, 2354.23),
                Account("2", false, 235.22),
            )
        ),
        User("5678", "Gustave", "Charbonneau", "T0pSecr3t",
            listOf(
                Account("3", false, 24.53),
                Account("4", true, 10032.21),
            )
        )
    )

    /**
     * Authentifie un utilisateur en comparant les informations d'identification fournies avec les données stockées.
     *
     * @param credentials Objet contenant l'ID et le mot de passe de l'utilisateur à authentifier.
     * @return Un objet CredentialsResult indiquant si l'authentification a réussi (true) ou échoué (false).
     */
    override fun login(credentials: Credentials): CredentialsResult {
        val user = getUserById(credentials.id)
        return CredentialsResult(user?.password == credentials.password)
    }

    /**
     * Récupère la liste des comptes associés à un utilisateur spécifique.
     *
     * @param id Identifiant unique de l'utilisateur dont les comptes doivent être récupérés.
     * @return Une liste d'objets Account représentant les comptes de l'utilisateur, ou une liste vide si aucun compte
     * n'est trouvé ou si l'utilisateur n'existe pas.
     */
    override fun accounts(id: String): List<Account> {
        val user = getUserById(id)
        return user?.accounts ?: emptyList()
    }

    /**
     * Effectue un transfert de fonds entre deux utilisateurs en vérifiant les conditions nécessaires.
     * Lève une exception si l'expéditeur ou le destinataire n'est pas trouvé, ou si le montant est invalide.
     *
     * @param transfer Objet contenant les détails du transfert (ID de l'expéditeur, ID du destinataire, montant à transférer).
     * @return Un objet TransferResult indiquant si le transfert a réussi (true) ou échoué (false).
     */
    override fun transfer(transfer: Transfer): TransferResult {
        // Récupère l'expéditeur en fonction de son ID. Si l'expéditeur n'est pas trouvé, une exception est lancée.
        val sender = getUserById(transfer.senderId) ?: throw IllegalArgumentException("The sender cannot be found")
        // Récupère le destinataire en fonction de son ID. Si le destinataire n'est pas trouvé, une exception est lancée.
        val recipient = getUserById(transfer.recipientId) ?: throw IllegalArgumentException("The recipient cannot be found")

        // Vérifie que le montant à transférer est positif. Sinon, lance une exception.
        if( transfer.amount < 0 ) {
            throw IllegalArgumentException("The amount to send cannot be negative")
        }

        // Récupère le compte principal de l'expéditeur. Si aucun n'existe, `mainAccountSender` sera `null`.
        val mainAccountSender = sender.accounts.firstOrNull { it.main == true }
        // Récupère le compte principal du destinataire. Si aucun n'existe, `recipientAccountSender` sera `null`.
        val recipientAccountSender = recipient.accounts.firstOrNull { it.main == true }

        // Vérifie si l'un des comptes principaux est introuvable ou si le solde de l'expéditeur est insuffisant.
        return if(mainAccountSender == null || recipientAccountSender == null || mainAccountSender.balance - transfer.amount < 0)
        {
            // Si une des conditions ci-dessus est vraie, le transfert échoue et retourne `TransferResult(false)`.
            TransferResult(false)
        }
        else {
            // Sinon, effectue le transfert en soustrayant le montant du solde de l'expéditeur
            mainAccountSender.balance -= transfer.amount
            // Et en ajoutant le montant au solde du destinataire
            recipientAccountSender.balance += transfer.amount

            // Retourne `TransferResult(true)` pour indiquer que le transfert a réussi.
            TransferResult(true)
        }
    }

    /**
     * Gets a user by their ID.
     * @param id The user's ID.
     * @return The user, or null if the user cannot be found.
     */
    /**
     * Recherche un utilisateur dans la liste par son identifiant.
     *
     * @param id Identifiant unique de l'utilisateur à rechercher.
     * @return L'objet User correspondant à l'identifiant, ou null si aucun utilisateur n'est trouvé.
     */
    private fun getUserById(id: String): User? {
        return users.firstOrNull { it.id == id }
    }

}