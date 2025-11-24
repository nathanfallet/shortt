package me.nathanfallet.shortt.api.responses.links

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
data class LinksResponse(
    val links: List<LinkResponse>,
)
