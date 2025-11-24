import {GetLinksUseCase} from "./GetLinksUseCase.ts";
import {Link} from "../../models/Link.ts";
import {LinksRepository} from "../../repositories/LinksRepository.ts";

export class GetLinksUseCaseImpl implements GetLinksUseCase {
    constructor(private repository: LinksRepository) {
    }

    invoke(forceRefresh?: boolean): Promise<Link[]> {
        return this.repository.findAll(forceRefresh);
    }
}
