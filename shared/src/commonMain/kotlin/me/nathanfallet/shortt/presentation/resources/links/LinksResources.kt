package me.nathanfallet.shortt.presentation.resources.links

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
@Resource("/links")
class Links {
    @Resource("{id}")
    class Id(val parent: Links = Links(), val id: Uuid)
}
