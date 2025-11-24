package me.nathanfallet.shortt.api.responses.auth

import kotlinx.serialization.Serializable
import me.nathanfallet.shortt.api.responses.users.CurrentUserResponse
import kotlin.js.JsExport

/**
 * Data class representing the response returned after a successful login.
 */
@Serializable
@JsExport
data class LoginResponse(
    /**
     * The current user's information.
     */
    val user: CurrentUserResponse,
    /**
     * The access token for authenticated requests.
     */
    val accessToken: String,
    /**
     * The refresh token used to obtain new access tokens.
     */
    val refreshToken: String,
)
