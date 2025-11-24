package me.nathanfallet.shortt.domain.services

import me.nathanfallet.shortt.domain.models.auth.TokenType
import kotlin.uuid.Uuid

/**
 * Interface for generating tokens.
 */
interface TokenService {
    /**
     * Generates a token for the given user ID and token type.
     *
     * @param userId The ID of the user.
     * @param type The type of token to generate.
     *
     * @return The generated token as a string.
     */
    fun generateToken(userId: Uuid, type: TokenType): String
}
