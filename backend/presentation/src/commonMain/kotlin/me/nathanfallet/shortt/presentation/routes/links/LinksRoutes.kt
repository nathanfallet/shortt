package me.nathanfallet.shortt.presentation.routes.links

import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.nathanfallet.shortt.api.resources.links.LinksApi
import me.nathanfallet.shortt.domain.usecases.links.GetLinksForUserUseCase
import me.nathanfallet.shortt.presentation.extensions.userId

data class LinksRoutesDependencies(
    val getLinksForUserUseCase: GetLinksForUserUseCase,
)

fun Route.linksRoutes(dependencies: LinksRoutesDependencies) = with(dependencies) {
    get<LinksApi> {
        val userId = call.userId()
        val links = getLinksForUserUseCase(userId)
        call.respond(links)
    }
    // TODO: More routes
}
