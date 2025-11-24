package me.nathanfallet.shortt.client

import me.nathanfallet.shortt.client.api.auth.AuthApiClient
import me.nathanfallet.shortt.client.api.links.LinksApiClient

interface ApiClient {
    val auth: AuthApiClient
    val links: LinksApiClient
}
