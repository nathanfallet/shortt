import {Link} from "../../models/Link.ts";

export interface CreateLinkUseCase {
    invoke(url: string, slug: string | null): Promise<Link>
}
