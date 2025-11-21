package me.nathanfallet.shortt.presentation.routes.links

import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.nathanfallet.shortt.domain.usecases.links.GetLinksForUserUseCase
import me.nathanfallet.shortt.presentation.extensions.userId
import me.nathanfallet.shortt.presentation.resources.links.Links
import kotlin.uuid.ExperimentalUuidApi

data class LinksRoutesDependencies(
    val getLinksForUserUseCase: GetLinksForUserUseCase,
)

@OptIn(ExperimentalUuidApi::class)
fun Route.linksRoutes(dependencies: LinksRoutesDependencies) = with(dependencies) {
    get<Links> {
        val userId = call.userId()
        val links = getLinksForUserUseCase(userId)
        call.respond(links)
    }
    // TODO: More routes
}
