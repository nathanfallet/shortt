package me.nathanfallet.shortt.domain.models.links

import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalJsExport::class, ExperimentalUuidApi::class)
@Serializable
@JsExport
data class Link(
    val id: Uuid,
    val userId: Uuid,
    // TODO: Add more
)
