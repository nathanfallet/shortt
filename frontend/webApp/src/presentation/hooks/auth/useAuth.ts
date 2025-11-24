import {useEffect} from 'react'
import {useAuthStore} from "../../stores/authStore.ts";

export const useAuth = () => {
    const {
        user,
        accessToken,
        refreshToken,
        isAuthenticated,
        isLoading,
        error,
        setUser,
        setLoading,
        setError,
        logout: clearAuth
    } = useAuthStore()

    // Check auth on mount
    useEffect(() => {
        const checkAuth = async () => {
            if (accessToken && !user) {
                setLoading(true)
                try {
                    // TODO: Implement getCurrentUser use case and uncomment
                    //const currentUser = await getCurrentUser.execute()
                    //setUser(currentUser)
                } catch (err) {
                    // Token invalid, clear auth
                    clearAuth()
                } finally {
                    setLoading(false)
                }
            }
        }

        void checkAuth()
    }, [accessToken, refreshToken])

    return {
        user,
        accessToken,
        refreshToken,
        isAuthenticated,
        isLoading,
        error
    }
}
