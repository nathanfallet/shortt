package me.nathanfallet.shortt.domain.models.users

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Data class representing a user in the system.
 */
@Serializable
data class User(
    /**
     * The unique identifier of the user.
     */
    val id: Uuid,
    /**
     * The username of the user.
     */
    val username: String,
    /**
     * The hashed password of the user.
     */
    val password: String,
)
