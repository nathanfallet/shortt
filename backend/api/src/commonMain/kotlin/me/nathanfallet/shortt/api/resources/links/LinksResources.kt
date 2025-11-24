package me.nathanfallet.shortt.api.resources.links

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Resource class representing link-related API endpoints.
 */
@Serializable
@Resource("/api/v1/links")
class LinksApi {
    /**
     * Resource class representing the endpoint for creating a new shortened link.
     */
    @Resource("{id}")
    class Id(
        /**
         * The parent LinksApi resource.
         */
        val parent: LinksApi = LinksApi(),
        /**
         * The unique identifier of the link.
         */
        val id: Uuid,
    )
}
