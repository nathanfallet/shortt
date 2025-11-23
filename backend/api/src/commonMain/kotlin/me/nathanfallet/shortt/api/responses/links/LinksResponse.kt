package me.nathanfallet.shortt.api.responses.links

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Serializable
@JsExport
data class LinksResponse(
    val links: List<String>, // Create a LinkResponse instead?
)
