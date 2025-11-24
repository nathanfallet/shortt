package me.nathanfallet.shortt.domain.usecases.links

import me.nathanfallet.shortt.domain.models.links.Link
import kotlin.uuid.Uuid

interface CreateLinkUseCase {
    suspend operator fun invoke(url: String, slug: String?, userId: Uuid): Link
}
