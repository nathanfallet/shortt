package me.nathanfallet.shortt.api.resources.auth

import io.ktor.resources.*
import kotlinx.serialization.Serializable

/**
 * Resource class representing authentication-related API endpoints.
 */
@Serializable
@Resource("/api/v1/auth")
class AuthApi {
    /**
     * Resource class representing the registration endpoint.
     */
    @Resource("register")
    class Register(
        /**
         * The parent AuthApi resource.
         */
        val parent: AuthApi = AuthApi(),
    )

    /**
     * Resource class representing the login endpoint.
     */
    @Resource("login")
    class Login(
        /**
         * The parent AuthApi resource.
         */
        val parent: AuthApi = AuthApi(),
    )
}
