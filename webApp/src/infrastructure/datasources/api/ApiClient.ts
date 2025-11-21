import axios, {AxiosInstance} from 'axios'

export class ApiClient {
    private client: AxiosInstance

    constructor(baseURL: string) {
        this.client = axios.create({
            baseURL,
            timeout: 10000,
            headers: {
                'Content-Type': 'application/json'
            }
        })

        // Interceptor pour auth token
        this.client.interceptors.request.use((config) => {
            const token = localStorage.getItem('auth_token')
            if (token) {
                config.headers.Authorization = `Bearer ${token}`
            }
            return config
        })

        // Interceptor pour erreurs
        this.client.interceptors.response.use(
            (response) => response,
            (error) => {
                if (error.response?.status === 401) {
                    // Redirect to login ou refresh token
                }
                throw error
            }
        )
    }

    get<T>(url: string) {
        return this.client.get<T>(url)
    }

    post<T>(url: string, data: any) {
        return this.client.post<T>(url, data)
    }

    put<T>(url: string, data: any) {
        return this.client.put<T>(url, data)
    }

    delete(url: string) {
        return this.client.delete(url)
    }
}
