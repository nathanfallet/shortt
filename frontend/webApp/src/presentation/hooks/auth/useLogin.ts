import {useState} from 'react'
import {LoginUseCase} from "../../../domain/usecases/auth/LoginUseCase.ts";
import {useAuthStore} from "../../stores/authStore.ts";

export const useLogin = (
    loginUseCase: LoginUseCase,
) => {
    const {setUser, setAccessToken, setRefreshToken, setError} = useAuthStore()

    const [isLoading, setIsLoading] = useState(false)

    const login = async (username: string, password: string) => {
        setIsLoading(true)
        setError(null)

        try {
            const result = await loginUseCase.invoke(username, password)

            setAccessToken(result.accessToken)
            setRefreshToken(result.refreshToken)
            setUser(result.user)

            return result.user
        } catch (err) {
            const message = err instanceof Error ? err.message : 'Login failed'
            setError(message)
            throw err
        } finally {
            setIsLoading(false)
        }
    }

    return {
        login,
        isLoading
    }
}
