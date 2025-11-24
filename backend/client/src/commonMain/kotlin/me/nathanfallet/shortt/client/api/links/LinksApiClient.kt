package me.nathanfallet.shortt.client.api.links

import me.nathanfallet.shortt.api.requests.links.CreateLinkRequest
import me.nathanfallet.shortt.api.responses.links.LinkResponse
import me.nathanfallet.shortt.api.responses.links.LinksResponse

interface LinksApiClient {
    suspend fun getAll(): LinksResponse
    suspend fun create(request: CreateLinkRequest): LinkResponse
}
