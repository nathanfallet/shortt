package me.nathanfallet.shortt.client

import me.nathanfallet.shortt.client.api.auth.AuthApiClient
import me.nathanfallet.shortt.client.api.links.LinksApiClient

/**
 * Interface representing the API client with different service clients.
 */
interface ApiClient {
    /**
     * The authentication API client.
     */
    val auth: AuthApiClient

    /**
     * The links API client.
     */
    val links: LinksApiClient
}
