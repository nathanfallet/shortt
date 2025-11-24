package me.nathanfallet.shortt.infrastructure.config

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import me.nathanfallet.shortt.domain.exceptions.auth.InvalidTokenException
import me.nathanfallet.shortt.domain.services.TokenService
import me.nathanfallet.shortt.infrastructure.jwt.JwtTokenService
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val rawTokenService by inject<TokenService>()
    val tokenService = rawTokenService as? JwtTokenService ?: error("TokenService must be a JwtTokenService")

    authentication {
        jwt("api-v1-jwt") {
            verifier(tokenService.verifier)
            validate { JWTPrincipal(it.payload) }
            challenge { _, _ -> throw InvalidTokenException() }
        }
    }
}
