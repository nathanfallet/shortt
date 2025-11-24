export type User = {
    id: string;
    username: string;
}

export type AuthenticatedUser = {
    user: User;
    accessToken: string;
    refreshToken: string;
}
