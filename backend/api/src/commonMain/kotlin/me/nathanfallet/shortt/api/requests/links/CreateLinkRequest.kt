package me.nathanfallet.shortt.api.requests.links

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@Serializable
@JsExport
data class CreateLinkRequest(
    val url: String,
)
