package me.nathanfallet.shortt.api.resources.users

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Resource class representing user-related API endpoints.
 */
@Serializable
@Resource("/api/v1/users")
class UsersApi {
    /**
     * Resource class representing the endpoint for a specific user by ID.
     */
    @Resource("{id}")
    class Id(
        /**
         * The parent UsersApi resource.
         */
        val parent: UsersApi = UsersApi(),
        /**
         * The unique identifier of the user.
         */
        val id: Uuid,
    )
}
