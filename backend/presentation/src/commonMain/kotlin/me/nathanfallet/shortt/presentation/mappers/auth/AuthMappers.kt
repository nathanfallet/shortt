package me.nathanfallet.shortt.presentation.mappers.auth

import me.nathanfallet.shortt.api.responses.auth.LoginResponse
import me.nathanfallet.shortt.api.responses.auth.RegisterResponse
import me.nathanfallet.shortt.domain.models.auth.AuthenticatedUser
import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.presentation.mappers.users.toCurrentUserResponse

/**
 * Maps an [AuthenticatedUser] to a [LoginResponse].
 *
 * @returns The mapped [LoginResponse].
 */
fun AuthenticatedUser.toLoginResponse() = LoginResponse(
    user = user.toCurrentUserResponse(),
    accessToken = accessToken,
    refreshToken = refreshToken,
)

/**
 * Maps a [User] to a [RegisterResponse].
 *
 * @returns The mapped [RegisterResponse].
 */
fun User.toRegisterResponse() = RegisterResponse(
    user = toCurrentUserResponse(),
)
