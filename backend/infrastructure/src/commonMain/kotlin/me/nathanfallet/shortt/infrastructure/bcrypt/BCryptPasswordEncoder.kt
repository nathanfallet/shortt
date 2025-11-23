package me.nathanfallet.shortt.infrastructure.bcrypt

import at.favre.lib.crypto.bcrypt.BCrypt
import me.nathanfallet.shortt.domain.services.PasswordEncoder

class BCryptPasswordEncoder : PasswordEncoder {

    override fun encode(rawPassword: String): String =
        BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray())

    override fun matches(rawPassword: String, encodedPassword: String): Boolean =
        BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword).verified

}
