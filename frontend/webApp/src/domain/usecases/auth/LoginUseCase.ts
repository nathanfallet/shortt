import {AuthenticatedUser} from "../../models/User.ts";

export interface LoginUseCase {
    invoke(username: string, password: string): Promise<AuthenticatedUser>
}
