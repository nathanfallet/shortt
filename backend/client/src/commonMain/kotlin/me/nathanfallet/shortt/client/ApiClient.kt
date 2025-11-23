package me.nathanfallet.shortt.client

import me.nathanfallet.shortt.client.api.auth.AuthApiClient

interface ApiClient {
    val auth: AuthApiClient
}
