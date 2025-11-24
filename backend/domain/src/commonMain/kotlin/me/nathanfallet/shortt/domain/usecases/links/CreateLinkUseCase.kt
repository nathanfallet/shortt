package me.nathanfallet.shortt.domain.usecases.links

import me.nathanfallet.shortt.domain.models.links.Link
import kotlin.uuid.Uuid

/**
 * Use case for creating a new link.
 */
interface CreateLinkUseCase {
    /**
     * Creates a new link with the given URL and optional slug for the specified user.
     * A random slug will be generated if none is provided.
     *
     * @param url The original URL to be shortened.
     * @param slug An optional custom slug for the shortened link.
     *
     * @return The created Link object.
     */
    suspend operator fun invoke(url: String, slug: String?, userId: Uuid): Link
}
