package me.nathanfallet.shortt.domain.repositories.links

import me.nathanfallet.shortt.domain.models.links.Link
import kotlin.uuid.Uuid

/**
 * Repository interface for managing links.
 */
interface LinksRepository {
    /**
     * Finds all links created by a specific user.
     *
     * @param userId The unique identifier of the user.
     *
     * @return A list of links created by the user.
     */
    suspend fun findByUserId(userId: Uuid): List<Link>

    /**
     * Finds a link by its unique identifier.
     *
     * @param id The unique identifier of the link.
     *
     * @return The link if found, or null if not found.
     */
    suspend fun findById(id: Uuid): Link?

    /**
     * Creates a new link.
     *
     * @param url The original URL to be shortened.
     * @param slug The custom slug for the shortened link.
     * @param userId The unique identifier of the user creating the link.
     *
     * @return The created link.
     */
    suspend fun create(url: String, slug: String, userId: Uuid): Link
}
