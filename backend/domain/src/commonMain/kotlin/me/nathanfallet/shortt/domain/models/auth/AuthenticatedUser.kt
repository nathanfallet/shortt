package me.nathanfallet.shortt.domain.models.auth

import kotlinx.serialization.Serializable
import me.nathanfallet.shortt.domain.models.users.User

@Serializable
data class AuthenticatedUser(
    val user: User,
    val accessToken: String,
    val refreshToken: String,
)
