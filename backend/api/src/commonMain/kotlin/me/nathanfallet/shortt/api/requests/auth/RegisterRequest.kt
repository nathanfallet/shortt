package me.nathanfallet.shortt.api.requests.auth

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
data class RegisterRequest(
    val username: String,
    val password: String,
)
