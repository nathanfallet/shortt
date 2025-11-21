import {ApiClient} from "./ApiClient.ts";
import {Link} from "../../../domain/models/Link.ts";

export class LinksApiDataSource {
    constructor(private client: ApiClient) {
    }

    async getAll(): Promise<Link[]> {
        // TODO: Use types from shared
        const response = await this.client.get<{ links: Link[] }>('/links')
        return response.data.links
    }
}
