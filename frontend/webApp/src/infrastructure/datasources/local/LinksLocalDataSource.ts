import {Link} from "../../../domain/models/Link.ts";
import {Database} from "./Database.ts";

export class LinksLocalDataSource {
    constructor(private db: Database) {
    }

    async getAll(): Promise<Link[]> {
        return this.db.links.toArray()
    }

    async saveAll(links: Link[]): Promise<void> {
        await this.db.links.clear()
        await this.db.links.bulkAdd(links.map(l => ({
            ...l,
            syncStatus: 'synced'
        })))
    }

    async create(link: Link): Promise<Link> {
        await this.db.links.add(link)
        return link
    }
}
