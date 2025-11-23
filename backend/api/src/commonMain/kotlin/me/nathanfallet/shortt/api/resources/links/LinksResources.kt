package me.nathanfallet.shortt.api.resources.links

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
@Resource("/links")
class Links {
    @Resource("{id}")
    class Id(val parent: Links = Links(), val id: Uuid)
}
