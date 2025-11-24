import {CreateLinkUseCase} from "./CreateLinkUseCase.ts";
import {Link} from "../../models/Link.ts";
import {LinksRepository} from "../../repositories/LinksRepository.ts";

export class CreateLinkUseCaseImpl implements CreateLinkUseCase {
    constructor(private repository: LinksRepository) {
    }

    async invoke(url: string, slug: string | null): Promise<Link> {
        return this.repository.create(url, slug);
    }
}
