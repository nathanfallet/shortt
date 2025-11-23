package me.nathanfallet.shortt.domain.repositories.links

import me.nathanfallet.shortt.domain.models.links.Link
import kotlin.uuid.Uuid

interface LinksRepository {

    suspend fun findByUserId(userId: Uuid): List<Link>
    suspend fun findById(id: Uuid): Link?

}
