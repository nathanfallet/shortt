package me.nathanfallet.shortt.presentation.di

import me.nathanfallet.shortt.presentation.routes.auth.AuthRoutesDependencies
import me.nathanfallet.shortt.presentation.routes.links.LinksRoutesDependencies
import me.nathanfallet.shortt.presentation.routes.users.UsersRoutesDependencies
import org.koin.dsl.module

/**
 * Koin module for presentation layer dependencies.
 */
val presentationModule = module {
    single { AuthRoutesDependencies(get(), get()) }
    single { UsersRoutesDependencies(get(), get()) }
    single { LinksRoutesDependencies(get(), get()) }
}
