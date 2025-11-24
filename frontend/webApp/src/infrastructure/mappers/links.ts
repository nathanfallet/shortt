import {LinkResponse} from "api";
import {Link} from "../../domain/models/Link.ts";

export const linkResponseToLink = (linkResponse: LinkResponse): Link => ({
    id: linkResponse.id,
    url: linkResponse.url,
    slug: linkResponse.slug
})
