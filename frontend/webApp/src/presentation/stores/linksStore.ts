import {create} from 'zustand'
import {Link} from "../../domain/models/Link.ts";

interface LinksState {
    links: Link[]
    isLoading: boolean
    error: string | null
    setLinks: (links: Link[]) => void
    addLink: (link: Link) => void
    removeLink: (id: string) => void
    setLoading: (loading: boolean) => void
    setError: (error: string | null) => void
    reset: () => void
}

export const useLinksStore = create<LinksState>((set) => ({
    // Initial state
    links: [],
    isLoading: false,
    error: null,

    // Actions
    setLinks: (links) => set({links, error: null}),

    addLink: (link) => set((state) => ({
        links: [link, ...state.links]
    })),

    removeLink: (id) => set((state) => ({
        links: state.links.filter(l => l.id !== id)
    })),

    setLoading: (isLoading) => set({isLoading}),

    setError: (error) => set({error}),

    reset: () => set({
        links: [],
        isLoading: false,
        error: null
    })
}))
