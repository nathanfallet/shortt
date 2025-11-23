package me.nathanfallet.shortt.infrastructure.jwt

import me.nathanfallet.shortt.domain.models.auth.TokenType
import me.nathanfallet.shortt.domain.services.TokenGenerator
import kotlin.uuid.Uuid

class JwtTokenGenerator(

) : TokenGenerator {
    override fun generateToken(userId: Uuid, type: TokenType): String {
        return ""
    }
}
