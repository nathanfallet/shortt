package me.nathanfallet.shortt.api

import kotlinx.serialization.json.Json

/**
 * Object providing a configured instance of Json for serialization and deserialization.
 */
object Serialization {
    /**
     * Configured Json instance that ignores unknown keys and does not include explicit nulls during serialization.
     */
    val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }
}
