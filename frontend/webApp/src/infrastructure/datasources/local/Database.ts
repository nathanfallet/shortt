import Dexie, {Table} from 'dexie'
import {Link} from "../../../domain/models/Link.ts";

export class Database extends Dexie {
    links!: Table<Link, number>

    constructor() {
        super('URLShortenerDB')

        this.version(1).stores({
            links: '++id, slug, userId, createdAt, syncStatus'
        })
    }
}
