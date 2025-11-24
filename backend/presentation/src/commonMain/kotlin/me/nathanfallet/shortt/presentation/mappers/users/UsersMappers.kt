package me.nathanfallet.shortt.presentation.mappers.users

import me.nathanfallet.shortt.api.responses.users.CurrentUserResponse
import me.nathanfallet.shortt.domain.models.users.User

/**
 * Maps a [User] to a [CurrentUserResponse].
 *
 * @returns The mapped [CurrentUserResponse].
 */
fun User.toCurrentUserResponse() = CurrentUserResponse(
    id = id,
    username = username,
)
