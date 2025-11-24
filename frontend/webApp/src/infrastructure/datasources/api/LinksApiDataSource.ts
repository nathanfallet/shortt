import {ApiClient} from "./ApiClient.ts";
import {CreateLinkRequest, LinkResponseSchema, LinksResponse, LinksResponseSchema} from "api";

export class LinksApiDataSource {
    constructor(private client: ApiClient) {
    }

    async getAll(): Promise<LinksResponse> {
        const response = await this.client.get('/api/v1/links')
        return LinksResponseSchema.parse(response.data)
    }

    async create(request: CreateLinkRequest) {
        const response = await this.client.post('/api/v1/links', request)
        return LinkResponseSchema.parse(response.data)
    }
}
