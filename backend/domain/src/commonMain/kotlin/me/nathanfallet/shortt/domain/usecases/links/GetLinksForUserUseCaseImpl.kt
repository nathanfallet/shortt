package me.nathanfallet.shortt.domain.usecases.links

import me.nathanfallet.shortt.domain.models.links.Link
import me.nathanfallet.shortt.domain.repositories.links.LinksRepository
import kotlin.uuid.Uuid

/**
 * Implementation of the [GetLinksForUserUseCase] interface.
 */
class GetLinksForUserUseCaseImpl(
    private val linksRepository: LinksRepository,
) : GetLinksForUserUseCase {
    override suspend fun invoke(userId: Uuid): List<Link> {
        return linksRepository.findByUserId(userId)
    }
}
