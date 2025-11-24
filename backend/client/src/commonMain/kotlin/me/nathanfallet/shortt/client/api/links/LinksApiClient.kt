package me.nathanfallet.shortt.client.api.links

import me.nathanfallet.shortt.api.requests.links.CreateLinkRequest
import me.nathanfallet.shortt.api.responses.links.LinkResponse
import me.nathanfallet.shortt.api.responses.links.LinksResponse

/**
 * Interface representing the links API client.
 */
interface LinksApiClient {
    /**
     * Retrieves all shortened links.
     */
    suspend fun getAll(): LinksResponse

    /**
     * Creates a new shortened link with the provided request.
     */
    suspend fun create(request: CreateLinkRequest): LinkResponse
}
