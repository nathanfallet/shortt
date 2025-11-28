import {useState} from 'react'
import {RegisterUseCase} from "../../../domain/usecases/auth/RegisterUseCase.ts";
import {useAuthStore} from "../../stores/authStore.ts";

export const useRegister = (
    registerUseCase: RegisterUseCase,
) => {
    const {setError} = useAuthStore()

    const [isLoading, setIsLoading] = useState(false)

    const register = async (username: string, password: string) => {
        setIsLoading(true)
        setError(null)

        try {
            const result = await registerUseCase.invoke(username, password)

            return result
        } catch (err) {
            const message = err instanceof Error ? err.message : 'Registration failed'
            setError(message)
            throw err
        } finally {
            setIsLoading(false)
        }
    }

    return {
        register,
        isLoading
    }
}
