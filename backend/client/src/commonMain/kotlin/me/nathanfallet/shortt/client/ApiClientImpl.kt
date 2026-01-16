package me.nathanfallet.shortt.client

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import me.nathanfallet.shortt.api.Serialization
import me.nathanfallet.shortt.client.api.auth.AuthApiClientImpl
import me.nathanfallet.shortt.client.api.links.LinksApiClientImpl

/**
 * Implementation of the ApiClient interface.
 */
class ApiClientImpl(
    private val baseUrl: String,
    private val clientBuilder: (HttpClientConfig<out HttpClientEngineConfig>.() -> Unit) -> HttpClient = { config ->
        HttpClient(config)
    },
) : ApiClient {
    private val client = clientBuilder {
        expectSuccess = true
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
    override val links = LinksApiClientImpl(client)
}
