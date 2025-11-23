package me.nathanfallet.shortt.config

import io.ktor.server.application.*
import me.nathanfallet.shortt.domain.repositories.links.LinksRepository
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import me.nathanfallet.shortt.domain.services.MetricsCollector
import me.nathanfallet.shortt.domain.services.PasswordEncoder
import me.nathanfallet.shortt.domain.services.TokenGenerator
import me.nathanfallet.shortt.domain.usecases.auth.LoginUserUseCase
import me.nathanfallet.shortt.domain.usecases.auth.LoginUserUseCaseImpl
import me.nathanfallet.shortt.domain.usecases.auth.RegisterUserUseCase
import me.nathanfallet.shortt.domain.usecases.auth.RegisterUserUseCaseImpl
import me.nathanfallet.shortt.domain.usecases.links.GetLinksForUserUseCase
import me.nathanfallet.shortt.domain.usecases.links.GetLinksForUserUseCaseImpl
import me.nathanfallet.shortt.domain.usecases.users.GetUserByIdUseCase
import me.nathanfallet.shortt.domain.usecases.users.GetUserByIdUseCaseImpl
import me.nathanfallet.shortt.domain.usecases.users.GetUsersUseCase
import me.nathanfallet.shortt.domain.usecases.users.GetUsersUseCaseImpl
import me.nathanfallet.shortt.infrastructure.bcrypt.BCryptPasswordEncoder
import me.nathanfallet.shortt.infrastructure.database.DatabaseFactory
import me.nathanfallet.shortt.infrastructure.database.MySQLDatabaseFactory
import me.nathanfallet.shortt.infrastructure.database.TransactionManager
import me.nathanfallet.shortt.infrastructure.database.TransactionManagerImpl
import me.nathanfallet.shortt.infrastructure.database.repositories.links.LinksRepositoryImpl
import me.nathanfallet.shortt.infrastructure.database.repositories.users.UsersRepositoryImpl
import me.nathanfallet.shortt.infrastructure.jwt.JwtTokenGenerator
import me.nathanfallet.shortt.infrastructure.observability.OpenTelemetryMetrics
import me.nathanfallet.shortt.infrastructure.observability.TelemetryFactory
import me.nathanfallet.shortt.infrastructure.observability.TelemetryFactoryImpl
import me.nathanfallet.shortt.presentation.routes.auth.AuthRoutesDependencies
import me.nathanfallet.shortt.presentation.routes.links.LinksRoutesDependencies
import me.nathanfallet.shortt.presentation.routes.users.UsersRoutesDependencies
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureDI() {
    install(Koin) {
        val applicationModule = module {
            single<DatabaseFactory> {
                MySQLDatabaseFactory(
                    get(),
                    environment.config.property("database.host").getString(),
                    environment.config.property("database.port").getString().toIntOrNull() ?: 3306,
                    environment.config.property("database.name").getString(),
                    environment.config.property("database.user").getString(),
                    environment.config.property("database.password").getString(),
                    environment.config.property("database.useSSL").getString().toBooleanStrictOrNull() == true,
                    environment.config.property("database.sslMode").getString(),
                    environment.config.property("database.maximumPoolSize").getString().toIntOrNull() ?: 10,
                )
            }
            single<TransactionManager> { TransactionManagerImpl(get()) }
            single<TelemetryFactory> { TelemetryFactoryImpl() }
            single<MetricsCollector> { OpenTelemetryMetrics(get()) }
            single<PasswordEncoder> { BCryptPasswordEncoder() }
            single<TokenGenerator> { JwtTokenGenerator() }
        }
        val authModule = module {
            single<RegisterUserUseCase> { RegisterUserUseCaseImpl(get(), get()) }
            single<LoginUserUseCase> { LoginUserUseCaseImpl(get(), get(), get()) }
            single { AuthRoutesDependencies(get(), get()) }
        }
        val usersModule = module {
            single<UsersRepository> { UsersRepositoryImpl(get()) }
            single<GetUsersUseCase> { GetUsersUseCaseImpl(get()) }
            single<GetUserByIdUseCase> { GetUserByIdUseCaseImpl(get()) }
            single { UsersRoutesDependencies(get(), get()) }
        }
        val linksModule = module {
            single<LinksRepository> { LinksRepositoryImpl(get()) }
            single<GetLinksForUserUseCase> { GetLinksForUserUseCaseImpl(get()) }
            single { LinksRoutesDependencies(get()) }
        }
        modules(
            applicationModule,
            authModule,
            usersModule,
            linksModule
        )
    }
}
