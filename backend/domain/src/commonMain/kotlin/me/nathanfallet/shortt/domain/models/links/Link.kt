package me.nathanfallet.shortt.domain.models.links

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Data class representing a shortened link.
 */
@Serializable
data class Link(
    /**
     * The unique identifier of the link.
     */
    val id: Uuid,
    /**
     * The unique identifier of the user who created the link.
     */
    val userId: Uuid,
    /**
     * The original URL that has been shortened.
     */
    val url: String,
    /**
     * The slug for the shortened link.
     */
    val slug: String,
)
