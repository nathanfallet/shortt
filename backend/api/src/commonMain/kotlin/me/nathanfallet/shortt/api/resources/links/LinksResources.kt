package me.nathanfallet.shortt.api.resources.links

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
@Resource("/api/v1/links")
class LinksApi {
    @Resource("{id}")
    class Id(val parent: LinksApi = LinksApi(), val id: Uuid)
}
