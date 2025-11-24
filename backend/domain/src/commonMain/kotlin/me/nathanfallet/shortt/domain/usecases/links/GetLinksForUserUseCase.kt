package me.nathanfallet.shortt.domain.usecases.links

import me.nathanfallet.shortt.domain.models.links.Link
import kotlin.uuid.Uuid

/**
 * Use case for retrieving all links associated with a specific user.
 */
interface GetLinksForUserUseCase {
    /**
     * Retrieves all links for the specified user.
     *
     * @param userId The ID of the user whose links are to be retrieved.
     *
     * @return A list of Link objects associated with the user.
     */
    suspend operator fun invoke(userId: Uuid): List<Link>
}
