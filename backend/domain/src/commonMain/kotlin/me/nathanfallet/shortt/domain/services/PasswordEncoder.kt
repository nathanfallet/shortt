package me.nathanfallet.shortt.domain.services

/**
 * Interface for encoding and verifying passwords.
 */
interface PasswordEncoder {
    /**
     * Encodes the raw password.
     *
     * @param rawPassword The raw password to encode.
     *
     * @return The encoded password.
     */
    fun encode(rawPassword: String): String

    /**
     * Checks if the raw password matches the encoded password.
     *
     * @param rawPassword The raw password to check.
     * @param encodedPassword The encoded password to compare against.
     *
     * @return True if the passwords match, false otherwise.
     */
    fun matches(rawPassword: String, encodedPassword: String): Boolean
}
