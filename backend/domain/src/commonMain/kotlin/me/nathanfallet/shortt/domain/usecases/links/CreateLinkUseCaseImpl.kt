package me.nathanfallet.shortt.domain.usecases.links

import me.nathanfallet.shortt.domain.models.links.Link
import me.nathanfallet.shortt.domain.repositories.links.LinksRepository
import kotlin.uuid.Uuid

class CreateLinkUseCaseImpl(
    private val linksRepository: LinksRepository,
) : CreateLinkUseCase {
    override suspend fun invoke(url: String, slug: String?, userId: Uuid): Link {
        val finalSlug = slug ?: ""
        // TODO: Validate slug uniqueness + slug generation logic
        return linksRepository.create(url, finalSlug, userId)
    }
}
