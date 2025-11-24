package me.nathanfallet.shortt.presentation.mappers.links

import me.nathanfallet.shortt.api.responses.links.LinkResponse
import me.nathanfallet.shortt.domain.models.links.Link

fun Link.toLinkResponse() = LinkResponse(
    id = id,
    url = url,
    slug = slug,
)
