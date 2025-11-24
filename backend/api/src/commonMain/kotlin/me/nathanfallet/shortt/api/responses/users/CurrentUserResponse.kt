package me.nathanfallet.shortt.api.responses.users

import digital.guimauve.zodable.Zodable
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.uuid.Uuid

/**
 * Data class representing the response containing the current user's information.
 */
@Serializable
@JsExport
@Zodable
data class CurrentUserResponse(
    /**
     * The unique identifier of the user.
     */
    val id: Uuid,
    /**
     * The username of the user.
     */
    val username: String,
)
