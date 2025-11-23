package me.nathanfallet.shortt.api.resources.users

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
@Resource("/api/v1/users")
class UsersApi {
    @Resource("{id}")
    class Id(val parent: UsersApi = UsersApi(), val id: Uuid)
}
