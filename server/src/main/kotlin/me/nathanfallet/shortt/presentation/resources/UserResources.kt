package me.nathanfallet.shortt.presentation.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
@Resource("/users")
class Users {
    @Resource("{id}")
    class Id(val parent: Users = Users(), val id: Uuid)
}
