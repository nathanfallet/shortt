package me.nathanfallet.shortt.api.requests.links

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
data class CreateLinkRequest(
    val url: String,
)
