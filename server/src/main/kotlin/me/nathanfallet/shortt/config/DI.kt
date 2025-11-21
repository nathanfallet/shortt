package me.nathanfallet.shortt.config

import io.ktor.server.application.*
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import me.nathanfallet.shortt.domain.usecases.users.*
import me.nathanfallet.shortt.infrastructure.database.repositories.users.UsersRepositoryImpl
import me.nathanfallet.shortt.presentation.routes.users.UsersRoutesDependencies
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureDI() {
    install(Koin) {
        val applicationModule = module {

        }
        val usersModule = module {
            single<UsersRepository> { UsersRepositoryImpl() }
            single<GetUsersUseCase> { GetUsersUseCaseImpl(get()) }
            single<CreateUserUseCase> { CreateUserUseCaseImpl(get()) }
            single<GetUserByIdUseCase> { GetUserByIdUseCaseImpl(get()) }
            single { UsersRoutesDependencies(get(), get(), get()) }
        }
        val linksModule = module {

        }
        modules(applicationModule, usersModule, linksModule)
    }
}
