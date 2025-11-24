import {useEffect} from "react";
import {GetLinksUseCase} from "../../../domain/usecases/links/GetLinksUseCase.ts";
import {useLinksStore} from "../../stores/linksStore.ts";

export const useLinks = (
    getLinksUseCase: GetLinksUseCase,
) => {
    const {links, isLoading, error, setLinks, setLoading, setError} = useLinksStore()

    // Load links
    const loadLinks = async (forceRefresh = false) => {
        setLoading(true)
        setError(null)

        try {
            const result = await getLinksUseCase.invoke(forceRefresh)
            setLinks(result)
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Unknown error')
        } finally {
            setLoading(false)
        }
    }

    // Auto-load on mount
    useEffect(() => {
        void loadLinks()
    }, [])

    return {
        links,
        isLoading,
        error,
        loadLinks,
        refresh: () => loadLinks(true)
    }
}
