import {CurrentUserResponse, LoginResponse} from "api";
import {AuthenticatedUser, User} from "../../domain/models/User.ts";

export const currentUserResponseToUser = (user: CurrentUserResponse): User => ({
    id: user.id,
    username: user.username
})

export const loginResponseToAuthenticatedUser = (response: LoginResponse): AuthenticatedUser => ({
    user: currentUserResponseToUser(response.user),
    accessToken: response.accessToken,
    refreshToken: response.refreshToken
})
