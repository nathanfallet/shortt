package me.nathanfallet.shortt.api.responses.users

import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.uuid.Uuid

@Serializable
@JsExport
data class CurrentUserResponse(
    val id: Uuid,
    val username: String,
)
