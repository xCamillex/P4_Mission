package com.aura.data

import com.aura.data.model.login.Credentials
import com.aura.data.model.repository.ApiRepository
import com.aura.data.model.transfer.Transfer
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

/**
 * The main entry point for the application.
 * le point d'entrée principal de l'application.
 */
fun main() {
    // Starts an embedded Netty server on port 8080. Démarre un serveur Netty intégré sur le port 8080.
    embeddedServer(Netty, port = 8080) {
        // Configure la négociation de contenu pour utiliser le format JSON avec des options de sérialisation lisibles et flexibles.
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        // Installs the CORS feature, which allows the server to respond to requests from any host.
        // Installe la fonctionnalité CORS, qui permet au serveur de répondre aux requêtes de n'importe quel hôte.
        install(CORS) {
            anyHost()
            allowHeader(HttpHeaders.ContentType)
        }

        // Defines the routing for the server. Définit le routage pour le serveur.
        routing {
            // Defines a POST endpoint for logging in users. Définit un point de terminaison POST pour la connexion des utilisateurs.
            post("/login") {
                val credentials = call.receive<Credentials>()
                call.respond(ApiRepository.login(credentials))
            }

            // Defines a GET endpoint for fetching a user's account information.
            // Définit un point de terminaison GET pour récupérer les informations de compte d'un utilisateur.
            get("/accounts/{id}") {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("Missing id path params")
                call.respond(ApiRepository.accounts(id))
            }

            // Defines a POST endpoint for transferring money between accounts.
            // Définit un point de terminaison POST pour transférer de l'argent entre les comptes.
            post("transfer") {
                val transfer = call.receive<Transfer>()
                call.respond(ApiRepository.transfer(transfer))
            }

            // Serves the Swagger UI documentation, which allows clients to explore the API.
            // Fournit la documentation de l'interface utilisateur Swagger, qui permet aux clients d'explorer l'API.
            swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        }
    }.start(wait = true)
}