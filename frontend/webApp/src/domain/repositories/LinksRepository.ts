import {Link} from "../models/Link.ts";

export interface LinksRepository {
    findAll(forceRefresh?: boolean): Promise<Link[]>

    create(url: string, slug: string | null): Promise<Link>
}
