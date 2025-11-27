package me.nathanfallet.shortt.infrastructure.di

import io.ktor.server.application.*
import me.nathanfallet.shortt.domain.repositories.links.LinksRepository
import me.nathanfallet.shortt.domain.repositories.users.UsersRepository
import me.nathanfallet.shortt.domain.services.MetricsCollectorService
import me.nathanfallet.shortt.domain.services.PasswordEncoderService
import me.nathanfallet.shortt.domain.services.TokenService
import me.nathanfallet.shortt.infrastructure.bcrypt.BCryptPasswordEncoderService
import me.nathanfallet.shortt.infrastructure.database.*
import me.nathanfallet.shortt.infrastructure.database.repositories.links.LinksRepositoryImpl
import me.nathanfallet.shortt.infrastructure.database.repositories.users.UsersRepositoryImpl
import me.nathanfallet.shortt.infrastructure.jwt.JwtTokenService
import me.nathanfallet.shortt.infrastructure.messaging.MessageBroker
import me.nathanfallet.shortt.infrastructure.messaging.RabbitMQFactory
import me.nathanfallet.shortt.infrastructure.messaging.RabbitMQFactoryImpl
import me.nathanfallet.shortt.infrastructure.messaging.RabbitMQMessageBroker
import me.nathanfallet.shortt.infrastructure.observability.OpenTelemetryMetricsService
import me.nathanfallet.shortt.infrastructure.observability.TelemetryFactory
import me.nathanfallet.shortt.infrastructure.observability.TelemetryFactoryImpl
import org.koin.dsl.module

/**
 * Koin module for infrastructure layer dependencies.
 */
val Application.infrastructureModule
    get() = module {
        single<Application> { this@infrastructureModule }

        // Services
        single<DatabaseFactory> {
            when (environment.config.property("database.protocol").getString()) {
                "mysql" -> MySQLDatabaseFactory(
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

                "h2" -> H2DatabaseFactory(
                    environment.config.property("database.name").getString(),
                )

                else -> error("Unsupported database protocol")
            }
        }
        single<TransactionManager> { TransactionManagerImpl(get()) }
        single<RabbitMQFactory> {
            RabbitMQFactoryImpl(
                get<Application>(),
                environment.config.property("rabbitmq.host").getString(),
                environment.config.property("rabbitmq.port").getString().toIntOrNull() ?: 5672,
                environment.config.property("rabbitmq.user").getString(),
                environment.config.property("rabbitmq.password").getString(),
                get(),
            )
        }
        single<MessageBroker> { RabbitMQMessageBroker(get()) }
        single<TelemetryFactory> { TelemetryFactoryImpl() }
        single<MetricsCollectorService> { OpenTelemetryMetricsService(get()) }
        single<PasswordEncoderService> { BCryptPasswordEncoderService() }
        single<TokenService> {
            JwtTokenService(
                environment.config.property("jwt.secret").getString(),
                environment.config.property("jwt.issuer").getString(),
                environment.config.property("jwt.audience").getString()
            )
        }

        // Repositories
        single<UsersRepository> { UsersRepositoryImpl(get()) }
        single<LinksRepository> { LinksRepositoryImpl(get()) }
    }
