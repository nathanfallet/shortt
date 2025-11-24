package me.nathanfallet.shortt.api.responses.links

import digital.guimauve.zodable.Zodable
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Data class representing the response containing a list of shortened links.
 */
@Serializable
@JsExport
@Zodable
data class LinksResponse(
    /**
     * The list of shortened links.
     */
    val links: List<LinkResponse>,
)
