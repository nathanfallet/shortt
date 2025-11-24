package me.nathanfallet.shortt.client.api.links

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.http.*
import me.nathanfallet.shortt.api.requests.links.CreateLinkRequest
import me.nathanfallet.shortt.api.resources.links.LinksApi
import me.nathanfallet.shortt.api.responses.links.LinkResponse
import me.nathanfallet.shortt.api.responses.links.LinksResponse

/**
 * Implementation of the LinksApiClient interface using Ktor HttpClient.
 */
class LinksApiClientImpl(
    private val client: HttpClient,
) : LinksApiClient {
    override suspend fun getAll(): LinksResponse = client
        .get(LinksApi())
        .body()

    override suspend fun create(request: CreateLinkRequest): LinkResponse = client
        .post(LinksApi()) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        .body()
}
