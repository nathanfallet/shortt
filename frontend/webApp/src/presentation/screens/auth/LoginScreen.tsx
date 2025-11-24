import {FormEvent, useMemo, useState} from 'react'
import {useLocation, useNavigate} from 'react-router-dom'
import {useAuthStore} from "../../stores/authStore.ts";
import {useLogin} from "../../hooks/auth/useLogin.ts";
import container from "../../../di/container.ts";
import {LoginUseCase} from "../../../domain/usecases/auth/LoginUseCase.ts";

export const LoginScreen = () => {
    // TODO: Is it the right place to resolve the use case?
    const loginUseCase = useMemo(() => container.resolve<LoginUseCase>("loginUseCase"), [])

    const navigate = useNavigate()
    const location = useLocation()
    const {login, isLoading} = useLogin(loginUseCase)
    const error = useAuthStore((state) => state.error)

    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const from = location.state?.from?.pathname || '/dashboard'

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault()

        try {
            await login(username, password)
            navigate(from, {replace: true})
        } catch {
            // Error handled in hook
        }
    }

    return (
        <div className="min-h-screen flex items-center justify-center">
            <form onSubmit={handleSubmit} className="w-full max-w-md p-8">
                <h1 className="text-2xl font-bold mb-6">Login</h1>

                {error ? (
                    <div className="bg-red-100 text-red-700 p-3 rounded mb-4">
                        {error}
                    </div>
                ) : null}

                <div className="mb-4">
                    <label className="block mb-2">Username</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className="w-full p-2 border rounded"
                        required
                    />
                </div>

                <div className="mb-6">
                    <label className="block mb-2">Password</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="w-full p-2 border rounded"
                        required
                    />
                </div>

                <button
                    type="submit"
                    disabled={isLoading}
                    className="w-full bg-blue-500 text-white p-2 rounded disabled:opacity-50"
                >
                    {isLoading ? 'Logging in...' : 'Login'}
                </button>
            </form>
        </div>
    )
}
