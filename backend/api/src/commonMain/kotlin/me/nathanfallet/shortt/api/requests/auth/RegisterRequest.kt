package me.nathanfallet.shortt.api.requests.auth

import digital.guimauve.zodable.Zodable
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Data class representing a registration request with username and password.
 */
@Serializable
@JsExport
@Zodable
data class RegisterRequest(
    /**
     * The username of the user attempting to register.
     */
    val username: String,
    /**
     * The password of the user attempting to register.
     */
    val password: String,
)
