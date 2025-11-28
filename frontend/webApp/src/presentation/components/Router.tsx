import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom'
import {LoginScreen} from "../screens/auth/LoginScreen.tsx";
import {RegisterScreen} from "../screens/auth/RegisterScreen.tsx";
import {LinksScreen} from "../screens/links/LinksScreen.tsx";
import {ProtectedRoute} from "./auth/ProtectedRoute.tsx";

export const Router = () => {
    return (
        <BrowserRouter>
            <Routes>
                {/* Public routes */}
                <Route path="/login" element={<LoginScreen/>}/>
                <Route path="/register" element={<RegisterScreen/>}/>

                {/* Protected routes */}
                <Route path="/" element={<Navigate to="/links" replace/>}/>
                <Route
                    path="/links"
                    element={
                        <ProtectedRoute>
                            <LinksScreen/>
                        </ProtectedRoute>
                    }
                />
            </Routes>
        </BrowserRouter>
    )
}
