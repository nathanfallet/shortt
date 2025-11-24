package me.nathanfallet.shortt.api.responses.links

import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.uuid.Uuid

/**
 * Data class representing the response for a shortened link.
 */
@Serializable
@JsExport
data class LinkResponse(
    /**
     * The unique identifier of the shortened link.
     */
    val id: Uuid,
    /**
     * The original URL that was shortened.
     */
    val url: String,
    /**
     * The slug used for the shortened link.
     */
    val slug: String,
)
