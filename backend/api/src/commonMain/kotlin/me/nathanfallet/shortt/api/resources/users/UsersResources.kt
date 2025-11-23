package me.nathanfallet.shortt.api.resources.users

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
@Resource("/users")
class Users {
    @Resource("{id}")
    class Id(val parent: Users = Users(), val id: Uuid)
}
