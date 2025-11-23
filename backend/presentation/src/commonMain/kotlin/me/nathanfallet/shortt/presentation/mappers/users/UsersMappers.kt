package me.nathanfallet.shortt.presentation.mappers.users

import me.nathanfallet.shortt.api.responses.users.CurrentUserResponse
import me.nathanfallet.shortt.domain.models.users.User

fun User.toCurrentUserResponse() = CurrentUserResponse(
    id = id,
    username = username,
)
