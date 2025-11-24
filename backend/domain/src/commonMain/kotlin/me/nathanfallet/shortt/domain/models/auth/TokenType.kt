package me.nathanfallet.shortt.domain.models.auth

/**
 * Enum representing the type of token.
 */
enum class TokenType {
    /**
     * Access token type.
     */
    ACCESS,

    /**
     * Refresh token type.
     */
    REFRESH
}
