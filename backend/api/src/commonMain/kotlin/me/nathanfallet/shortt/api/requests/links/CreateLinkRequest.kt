package me.nathanfallet.shortt.api.requests.links

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Data class representing a request to create a shortened link.
 */
@Serializable
@JsExport
data class CreateLinkRequest(
    /**
     * The original URL to be shortened.
     */
    val url: String,
    /**
     * An optional custom slug for the shortened link.
     */
    val slug: String? = null,
)
