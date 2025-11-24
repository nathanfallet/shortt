package me.nathanfallet.shortt.domain.models.auth

import kotlinx.serialization.Serializable
import me.nathanfallet.shortt.domain.models.users.User

/**
 * Data class representing an authenticated user along with their tokens.
 */
@Serializable
data class AuthenticatedUser(
    /**
     * The authenticated user's information.
     */
    val user: User,
    /**
     * The access token for authenticated requests.
     */
    val accessToken: String,
    /**
     * The refresh token used to get new access tokens.
     */
    val refreshToken: String,
)
