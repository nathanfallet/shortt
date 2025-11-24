package me.nathanfallet.shortt.infrastructure.di

import io.ktor.server.application.*
import me.nathanfallet.shortt.domain.repositories.links.LinksRepository
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import me.nathanfallet.shortt.domain.services.MetricsCollector
import me.nathanfallet.shortt.domain.services.PasswordEncoder
import me.nathanfallet.shortt.domain.services.TokenGenerator
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
import org.koin.dsl.module

/**
 * Koin module for infrastructure layer dependencies.
 */
val Application.infrastructureModule
    get() = module {
        // Services
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

        // Repositories
        single<UsersRepository> { UsersRepositoryImpl(get()) }
        single<LinksRepository> { LinksRepositoryImpl(get()) }
    }
