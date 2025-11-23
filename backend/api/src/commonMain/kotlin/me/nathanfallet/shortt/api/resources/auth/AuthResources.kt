package me.nathanfallet.shortt.api.resources.auth

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/api/v1/auth")
class AuthApi {
    @Resource("register")
    class Register(val parent: AuthApi = AuthApi())

    @Resource("login")
    class Login(val parent: AuthApi = AuthApi())
}
