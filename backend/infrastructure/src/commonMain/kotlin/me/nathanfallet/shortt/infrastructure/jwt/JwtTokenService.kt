package me.nathanfallet.shortt.infrastructure.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import me.nathanfallet.shortt.domain.models.auth.TokenType
import me.nathanfallet.shortt.domain.services.TokenService
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.uuid.Uuid

/**
 * JWT implementation of the [TokenService] interface.
 */
class JwtTokenService(
    private val secret: String,
    private val issuer: String,
    private val audience: String,
    private val accessTokenExpiration: Long = 7.days.inWholeMilliseconds,
    private val refreshTokenExpiration: Long = 365.days.inWholeMilliseconds,
) : TokenService {
    val verifier: JWTVerifier =
        JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    override fun generateToken(userId: Uuid, type: TokenType): String {
        val effectiveExpiration = when (type) {
            TokenType.REFRESH -> refreshTokenExpiration
            TokenType.ACCESS -> accessTokenExpiration
        }
        return JWT.create()
            .withSubject(userId.toString())
            .withAudience(audience)
            .withIssuer(issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + effectiveExpiration))
            .withClaim("type", type.name.lowercase())
            .sign(Algorithm.HMAC256(secret))
    }
}
