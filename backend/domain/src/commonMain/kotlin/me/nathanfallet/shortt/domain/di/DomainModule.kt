package me.nathanfallet.shortt.domain.di

import me.nathanfallet.shortt.domain.usecases.auth.LoginUserUseCase
import me.nathanfallet.shortt.domain.usecases.auth.LoginUserUseCaseImpl
import me.nathanfallet.shortt.domain.usecases.auth.RegisterUserUseCase
import me.nathanfallet.shortt.domain.usecases.auth.RegisterUserUseCaseImpl
import me.nathanfallet.shortt.domain.usecases.links.CreateLinkUseCase
import me.nathanfallet.shortt.domain.usecases.links.CreateLinkUseCaseImpl
import me.nathanfallet.shortt.domain.usecases.links.GetLinksForUserUseCase
import me.nathanfallet.shortt.domain.usecases.links.GetLinksForUserUseCaseImpl
import me.nathanfallet.shortt.domain.usecases.users.GetUserByIdUseCase
import me.nathanfallet.shortt.domain.usecases.users.GetUserByIdUseCaseImpl
import me.nathanfallet.shortt.domain.usecases.users.GetUsersUseCase
import me.nathanfallet.shortt.domain.usecases.users.GetUsersUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    // Auth
    single<RegisterUserUseCase> { RegisterUserUseCaseImpl(get(), get()) }
    single<LoginUserUseCase> { LoginUserUseCaseImpl(get(), get(), get()) }

    // Users
    single<GetUsersUseCase> { GetUsersUseCaseImpl(get()) }
    single<GetUserByIdUseCase> { GetUserByIdUseCaseImpl(get()) }

    // Links
    single<GetLinksForUserUseCase> { GetLinksForUserUseCaseImpl(get()) }
    single<CreateLinkUseCase> { CreateLinkUseCaseImpl(get()) }
}
