package me.nathanfallet.shortt.domain.usecases.links

import me.nathanfallet.shortt.domain.models.links.Link
import kotlin.uuid.Uuid

interface GetLinksForUserUseCase {
    suspend operator fun invoke(userId: Uuid): List<Link>
}
