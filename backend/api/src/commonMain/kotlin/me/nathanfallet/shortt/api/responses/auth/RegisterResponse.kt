package me.nathanfallet.shortt.api.responses.auth

import me.nathanfallet.shortt.api.responses.users.CurrentUserResponse

data class RegisterResponse(
    val user: CurrentUserResponse,
)
