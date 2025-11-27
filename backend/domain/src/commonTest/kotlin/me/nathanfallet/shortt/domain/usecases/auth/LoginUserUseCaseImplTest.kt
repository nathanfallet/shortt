package me.nathanfallet.shortt.domain.usecases.auth

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import me.nathanfallet.shortt.domain.exceptions.auth.InvalidCredentialsException
import me.nathanfallet.shortt.domain.models.auth.TokenType
import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import me.nathanfallet.shortt.domain.services.PasswordEncoderService
import me.nathanfallet.shortt.domain.services.TokenService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.uuid.Uuid

class LoginUserUseCaseImplTest {
    @Test
    fun testLoginUserDoesNotExists() = runTest {
        val repository = mockk<UsersRepository>()
        val useCase = LoginUserUseCaseImpl(repository, mockk(), mockk())
        coEvery { repository.findByUsername("nonexistentuser") } returns null
        assertFailsWith<InvalidCredentialsException> {
            useCase("nonexistentuser", "password")
        }
    }

    @Test
    fun testLoginUserWrongPassword() = runTest {
        val repository = mockk<UsersRepository>()
        val passwordEncoderService = mockk<PasswordEncoderService>()
        val useCase = LoginUserUseCaseImpl(repository, passwordEncoderService, mockk())
        val user = User(id = Uuid.random(), username = "testuser", password = "hashedpassword")
        coEvery { repository.findByUsername("testuser") } returns user
        coEvery { passwordEncoderService.matches("wrongpassword", "hashedpassword") } returns false
        assertFailsWith<InvalidCredentialsException> {
            useCase("testuser", "wrongpassword")
        }
    }

    @Test
    fun testLoginUserExistsAndCorrectPassword() = runTest {
        val repository = mockk<UsersRepository>()
        val passwordEncoderService = mockk<PasswordEncoderService>()
        val tokenService = mockk<TokenService>()
        val useCase = LoginUserUseCaseImpl(repository, passwordEncoderService, tokenService)
        val user = User(id = Uuid.random(), username = "testuser", password = "hashedpassword")
        coEvery { repository.findByUsername("testuser") } returns user
        coEvery { passwordEncoderService.matches("correctpassword", "hashedpassword") } returns true
        coEvery { tokenService.generateToken(user.id, TokenType.ACCESS) } returns "accessToken"
        coEvery { tokenService.generateToken(user.id, TokenType.REFRESH) } returns "refreshToken"
        val authenticatedUser = useCase("testuser", "correctpassword")
        assertEquals(user, authenticatedUser.user)
        assertEquals("accessToken", authenticatedUser.accessToken)
        assertEquals("refreshToken", authenticatedUser.refreshToken)
    }
}
