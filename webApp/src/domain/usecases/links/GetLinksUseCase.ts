import {Link} from "../../models/Link.ts";

export interface GetLinksUseCase {
    invoke(forceRefresh?: boolean): Promise<Link[]>
}
