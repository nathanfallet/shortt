package me.nathanfallet.shortt.presentation.responses.links

import kotlinx.serialization.Serializable
import me.nathanfallet.shortt.domain.models.links.Link
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Serializable
@JsExport
data class LinksResponse(
    val links: List<Link>, // Create a LinkResponse instead?
)
