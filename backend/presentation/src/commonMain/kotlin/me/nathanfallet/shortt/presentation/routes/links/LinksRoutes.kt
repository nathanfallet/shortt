package me.nathanfallet.shortt.presentation.routes.links

import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.nathanfallet.shortt.api.requests.links.CreateLinkRequest
import me.nathanfallet.shortt.api.resources.links.LinksApi
import me.nathanfallet.shortt.api.responses.links.LinksResponse
import me.nathanfallet.shortt.domain.usecases.links.CreateLinkUseCase
import me.nathanfallet.shortt.domain.usecases.links.GetLinksForUserUseCase
import me.nathanfallet.shortt.presentation.extensions.userId
import me.nathanfallet.shortt.presentation.mappers.links.toLinkResponse

data class LinksRoutesDependencies(
    val getLinksForUserUseCase: GetLinksForUserUseCase,
    val createLinkUseCase: CreateLinkUseCase,
)

fun Route.linksRoutes(dependencies: LinksRoutesDependencies) = with(dependencies) {
    get<LinksApi> {
        val userId = call.userId()
        val links = getLinksForUserUseCase(userId).map { it.toLinkResponse() }
        call.respond(LinksResponse(links))
    }
    post<LinksApi> {
        val userId = call.userId()
        val request = call.receive<CreateLinkRequest>()
        val link = createLinkUseCase(request.url, request.slug, userId)
        call.respond(link.toLinkResponse())
    }
}
