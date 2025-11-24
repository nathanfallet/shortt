import {create} from 'zustand'
import {persist} from 'zustand/middleware'
import {User} from "../../domain/models/User.ts";

interface AuthState {
    // State
    user: User | null
    accessToken: string | null
    refreshToken: string | null
    isAuthenticated: boolean
    isLoading: boolean
    error: string | null

    // Actions (setters only, no business logic)
    setUser: (user: User | null) => void
    setAccessToken: (accessToken: string | null) => void
    setRefreshToken: (refreshToken: string | null) => void
    setLoading: (loading: boolean) => void
    setError: (error: string | null) => void
    logout: () => void
}

export const useAuthStore = create<AuthState>()(
    persist(
        (set) => ({
            // Initial state
            user: null,
            accessToken: null,
            refreshToken: null,
            isAuthenticated: false,
            isLoading: false,
            error: null,

            // Actions
            setUser: (user) => set({
                user,
                isAuthenticated: user !== null
            }),

            setAccessToken: (accessToken: string | null) => set({accessToken}),

            setRefreshToken: (refreshToken: string | null) => set({refreshToken}),

            setLoading: (isLoading) => set({isLoading}),

            setError: (error) => set({error}),

            logout: () => set({
                user: null,
                accessToken: null,
                refreshToken: null,
                isAuthenticated: false,
                error: null
            })
        }),
        {
            name: 'auth-storage',  // localStorage key
            partialize: (state) => ({
                // Only persist these fields
                user: state.user,
                accessToken: state.accessToken,
                refreshToken: state.refreshToken,
                isAuthenticated: state.isAuthenticated
            })
        }
    )
)
