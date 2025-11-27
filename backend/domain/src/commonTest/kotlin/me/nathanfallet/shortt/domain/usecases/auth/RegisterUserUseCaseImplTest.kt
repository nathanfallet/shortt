package me.nathanfallet.shortt.domain.usecases.auth

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import me.nathanfallet.shortt.domain.exceptions.users.UserAlreadyExistsException
import me.nathanfallet.shortt.domain.models.users.User
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import me.nathanfallet.shortt.domain.services.PasswordEncoderService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.uuid.Uuid

class RegisterUserUseCaseImplTest {
    @Test
    fun testRegisterUserAlreadyExists() = runTest {
        val repository = mockk<UsersRepository>()
        val useCase = RegisterUserUseCaseImpl(repository, mockk())
        val existingUser = User(id = Uuid.random(), username = "existinguser", password = "hashedpassword")
        coEvery { repository.findByUsername("existinguser") } returns existingUser
        assertFailsWith<UserAlreadyExistsException> {
            useCase("existinguser", "password")
        }
    }

    @Test
    fun testRegisterUserDoesNotExist() = runTest {
        val repository = mockk<UsersRepository>()
        val passwordEncoderService = mockk<PasswordEncoderService>()
        val useCase = RegisterUserUseCaseImpl(repository, passwordEncoderService)
        val newUserId = Uuid.random()
        val newUser = User(id = newUserId, username = "newuser", password = "hashedpassword")
        coEvery { repository.findByUsername("newuser") } returns null
        coEvery { passwordEncoderService.encode("password") } returns "hashedpassword"
        coEvery { repository.create("newuser", "hashedpassword") } returns newUser
        val registeredUser = useCase("newuser", "password")
        assertEquals(newUser, registeredUser)
    }
}
