package me.nathanfallet.shortt.client

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import me.nathanfallet.shortt.api.Serialization
import me.nathanfallet.shortt.client.api.auth.AuthApiClientImpl

class ApiClientImpl(
    private val baseUrl: String,
) : ApiClient {
    private val client = HttpClient {
        install(Resources)
        install(ContentNegotiation) {
            json(Serialization.json)
        }
        /*
        install(Auth) {
            bearer {
                loadTokens { BearerTokens(getToken(), "") }
            }
        }
        */
        defaultRequest {
            url(baseUrl)
        }
    }

    override val auth = AuthApiClientImpl(client)
}
