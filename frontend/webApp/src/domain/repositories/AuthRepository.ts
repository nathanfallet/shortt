import {AuthenticatedUser, User} from "../models/User.ts";

export interface AuthRepository {
    login(username: string, password: string): Promise<AuthenticatedUser>

    register(username: string, password: string): Promise<User>
}
