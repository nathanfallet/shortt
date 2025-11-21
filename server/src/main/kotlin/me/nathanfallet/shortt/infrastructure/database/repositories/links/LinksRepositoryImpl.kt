package me.nathanfallet.shortt.infrastructure.database.repositories.links

import me.nathanfallet.shortt.domain.models.links.Link
import me.nathanfallet.shortt.domain.repositories.links.LinksRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class LinksRepositoryImpl(

) : LinksRepository {

    // TODO: Setup real database
    private val links = hashMapOf<Uuid, Link>()

    override suspend fun findByUserId(userId: Uuid): List<Link> {
        return links.values.filter { it.userId == userId }
    }

    override suspend fun findById(id: Uuid): Link? {
        return links[id]
    }

}
