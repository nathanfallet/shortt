package me.nathanfallet.shortt.api.responses.links

import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.uuid.Uuid

@Serializable
@JsExport
data class LinkResponse(
    val id: Uuid,
    val url: String,
    val slug: String,
)
