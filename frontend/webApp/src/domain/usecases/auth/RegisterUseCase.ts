import {User} from "../../models/User.ts";

export interface RegisterUseCase {
    invoke(username: string, password: string): Promise<User>
}
