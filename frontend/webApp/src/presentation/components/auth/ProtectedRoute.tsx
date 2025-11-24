import {Navigate, useLocation} from 'react-router-dom'
import React from "react";
import {useAuth} from "../../hooks/auth/useAuth.ts";

interface ProtectedRouteProps {
    children: React.ReactNode
}

export const ProtectedRoute = ({children}: ProtectedRouteProps) => {
    const {isAuthenticated, isLoading} = useAuth()
    const location = useLocation()

    if (isLoading) {
        return (
            <div className="flex items-center justify-center h-screen">
                <Spinner/>
            </div>
        )
    }

    if (!isAuthenticated) {
        // Redirect to login, save current location
        return <Navigate to="/login" state={{from: location}} replace/>
    }

    return <>{children}</>
}
