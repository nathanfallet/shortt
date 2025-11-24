package me.nathanfallet.shortt.api.requests.auth

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Data class representing a login request with username and password.
 */
@Serializable
@JsExport
data class LoginRequest(
    /**
     * The username of the user attempting to log in.
     */
    val username: String,
    /**
     * The password of the user attempting to log in.
     */
    val password: String,
)
