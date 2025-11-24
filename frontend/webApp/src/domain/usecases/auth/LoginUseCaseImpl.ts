import {LoginUseCase} from "./LoginUseCase.ts";
import {AuthRepository} from "../../repositories/AuthRepository.ts";
import {AuthenticatedUser} from "../../models/User.ts";

export class LoginUseCaseImpl implements LoginUseCase {
    constructor(private repository: AuthRepository) {
    }

    async invoke(username: string, password: string): Promise<AuthenticatedUser> {
        const authenticatedUser = await this.repository.login(username, password);
        if (authenticatedUser == null) throw new Error("Invalid credentials");

        // TODO: Save token etc...
        // Or maybe not, because it looks like the hook is doing it... (does it make sense tho?)

        return authenticatedUser;
    }
}
