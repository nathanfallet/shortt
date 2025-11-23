package me.nathanfallet.shortt.api.responses.auth

import kotlinx.serialization.Serializable
import me.nathanfallet.shortt.api.responses.users.CurrentUserResponse
import kotlin.js.JsExport

@Serializable
@JsExport
data class LoginResponse(
    val user: CurrentUserResponse,
    val accessToken: String,
    val refreshToken: String,
)
