import {LoginUseCase} from "./LoginUseCase.ts";
import {AuthRepository} from "../../repositories/AuthRepository.ts";
import {AuthenticatedUser} from "../../models/User.ts";

export class LoginUseCaseImpl implements LoginUseCase {
    constructor(private repository: AuthRepository) {
    }

    async invoke(username: string, password: string): Promise<AuthenticatedUser | null> {
        const authenticatedUser = await this.repository.login(username, password);
        // TODO: Save token etc...
        return authenticatedUser;
    }
}
