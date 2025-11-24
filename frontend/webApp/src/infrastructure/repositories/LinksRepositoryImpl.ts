import {LinksRepository} from "../../domain/repositories/LinksRepository.ts";
import {Link} from "../../domain/models/Link.ts";
import {LinksApiDataSource} from "../datasources/api/LinksApiDataSource.ts";
import {LinksLocalDataSource} from "../datasources/local/LinksLocalDataSource.ts";

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
            const links = await this.apiDataSource.getAll()
            await this.localDataSource.saveAll(links)
            return links
        } catch (error) {
            console.warn('API failed, using cached data', error)
            return this.localDataSource.getAll()
        }
    }
}
