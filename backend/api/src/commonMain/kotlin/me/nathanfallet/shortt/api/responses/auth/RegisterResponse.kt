package me.nathanfallet.shortt.api.responses.auth

import kotlinx.serialization.Serializable
import me.nathanfallet.shortt.api.responses.users.CurrentUserResponse
import kotlin.js.JsExport

/**
 * Data class representing the response returned after a successful registration.
 */
@Serializable
@JsExport
data class RegisterResponse(
    /**
     * The current user's information.
     */
    val user: CurrentUserResponse,
)
