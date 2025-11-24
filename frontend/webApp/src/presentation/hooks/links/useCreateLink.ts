import {CreateLinkUseCase} from "../../../domain/usecases/links/CreateLinkUseCase.ts";
import {useState} from "react";
import {useLinksStore} from "../../stores/linksStore.ts";

export const useCreateLink = (
    createLinkUseCase: CreateLinkUseCase
) => {
    const {addLink, setError} = useLinksStore()

    const [isCreating, setIsCreating] = useState(false)

    const createLink = async (url: string, customSlug?: string) => {
        setIsCreating(true)
        setError(null)

        try {
            const link = await createLinkUseCase.invoke(url, customSlug ?? null)
            addLink(link) // Update store
            return link
        } catch (err) {
            const message = err instanceof Error ? err.message : 'Failed to create link'
            setError(message)
            throw err
        } finally {
            setIsCreating(false)
        }
    }

    return {
        createLink,
        isCreating
    }
}
