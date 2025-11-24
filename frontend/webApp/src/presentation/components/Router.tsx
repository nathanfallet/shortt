import {BrowserRouter, Route, Routes} from 'react-router-dom'
import {LoginScreen} from "../screens/auth/LoginScreen.tsx";
import {ProtectedRoute} from "./auth/ProtectedRoute.tsx";

export const Router = () => {
    return (
        <BrowserRouter>
            <Routes>
                {/* Public routes */}
                <Route path="/login" element={<LoginScreen/>}/>
                {/*<Route path="/register" element={<RegisterScreen/>}/>*/}

                {/* Protected routes */}
                <Route
                    path="/"
                    element={
                        <ProtectedRoute>
                            <div>Layout Idk what that is but children is required</div>
                            {/*<Layout />*/}
                        </ProtectedRoute>
                    }
                >
                    {/*
                    <Route index element={<DashboardScreen/>}/>
                    <Route path="links" element={<LinksScreen/>}/>
                    <Route path="links/:id" element={<LinkDetailScreen/>}/>
                    */}
                </Route>
            </Routes>
        </BrowserRouter>
    )
}
