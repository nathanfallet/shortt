import {LinksRepository} from "../../domain/repositories/LinksRepository.ts";
import {Link} from "../../domain/models/Link.ts";
import {LinksApiDataSource} from "../datasources/api/LinksApiDataSource.ts";
import {LinksLocalDataSource} from "../datasources/local/LinksLocalDataSource.ts";
import {linkResponseToLink} from "../mappers/links.ts";
import {CreateLinkRequest} from "api";

export class LinksRepositoryImpl implements LinksRepository {
    constructor(
        private apiDataSource: LinksApiDataSource,
        private localDataSource: LinksLocalDataSource
    ) {
    }

    async findAll(forceRefresh?: boolean): Promise<Link[]> {
        if (!forceRefresh) {
            const cached = await this.localDataSource.getAll()
            if (cached.length > 0) return cached
        }
        try {
            const rawLinks = await this.apiDataSource.getAll()
            const links = rawLinks.links.map(link => linkResponseToLink(link))
            await this.localDataSource.saveAll(links)
            return links
        } catch (error) {
            console.warn('API failed, using cached data', error)
            return this.localDataSource.getAll()
        }
    }

    async create(url: string, slug: string | null): Promise<Link> {
        const request: CreateLinkRequest = {url, slug}
        const rawLink = await this.apiDataSource.create(request)
        const link = linkResponseToLink(rawLink)
        await this.localDataSource.create(link)
        return link
    }
}
