import {RegisterUseCase} from "./RegisterUseCase.ts";
import {AuthRepository} from "../../repositories/AuthRepository.ts";
import {User} from "../../models/User.ts";

export class RegisterUseCaseImpl implements RegisterUseCase {
    constructor(private repository: AuthRepository) {
    }

    async invoke(username: string, password: string): Promise<User> {
        const user = await this.repository.register(username, password);
        if (user == null) throw new Error("Registration failed");

        return user;
    }
}
