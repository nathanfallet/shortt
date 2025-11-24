import {AuthRepository} from "../../domain/repositories/AuthRepository.ts";
import {LoginRequest, LoginResponseSchema, RegisterRequest, RegisterResponseSchema} from "api";
import {ApiClient} from "../datasources/api/ApiClient.ts";
import {currentUserResponseToUser, loginResponseToAuthenticatedUser} from "../mappers/users.ts";
import {AuthenticatedUser, User} from "../../domain/models/User.ts";

export class AuthRepositoryImpl implements AuthRepository {
    constructor(private client: ApiClient) {
    }

    async login(username: string, password: string): Promise<AuthenticatedUser> {
        const request: LoginRequest = {username, password}
        const response = await this.client.post('/api/v1/auth/login', request);
        return loginResponseToAuthenticatedUser(LoginResponseSchema.parse(response.data))
    }

    async register(username: string, password: string): Promise<User> {
        const request: RegisterRequest = {username, password}
        const response = await this.client.post('/api/v1/auth/register', request);
        return currentUserResponseToUser(RegisterResponseSchema.parse(response.data).user)
    }
}
