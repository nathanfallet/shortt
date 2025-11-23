package me.nathanfallet.shortt.presentation.mappers.auth

import me.nathanfallet.shortt.api.responses.auth.LoginResponse
import me.nathanfallet.shortt.api.responses.auth.RegisterResponse
import me.nathanfallet.shortt.domain.models.auth.AuthenticatedUser
import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.presentation.mappers.users.toCurrentUserResponse

fun AuthenticatedUser.toLoginResponse() = LoginResponse(
    user = user.toCurrentUserResponse(),
    accessToken = accessToken,
    refreshToken = refreshToken,
)

fun User.toRegisterResponse() = RegisterResponse(
    user = toCurrentUserResponse(),
)
