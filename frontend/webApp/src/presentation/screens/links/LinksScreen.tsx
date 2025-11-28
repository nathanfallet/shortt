import {FormEvent, useMemo, useState} from 'react'
import container from "../../../di/container.ts";
import {GetLinksUseCase} from "../../../domain/usecases/links/GetLinksUseCase.ts";
import {CreateLinkUseCase} from "../../../domain/usecases/links/CreateLinkUseCase.ts";
import {useLinks} from "../../hooks/links/useLinks.ts";
import {useCreateLink} from "../../hooks/links/useCreateLink.ts";

export const LinksScreen = () => {
    const getLinksUseCase = useMemo(() => container.resolve<GetLinksUseCase>("getLinksUseCase"), [])
    const createLinkUseCase = useMemo(() => container.resolve<CreateLinkUseCase>("createLinkUseCase"), [])

    const {links, isLoading, error, refresh} = useLinks(getLinksUseCase)
    const {createLink, isCreating} = useCreateLink(createLinkUseCase)

    const [url, setUrl] = useState('')
    const [customSlug, setCustomSlug] = useState('')

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault()

        try {
            await createLink(url, customSlug || undefined)
            setUrl('')
            setCustomSlug('')
        } catch {
            // Error handled in hook
        }
    }

    return (
        <div className="min-h-screen p-8">
            <div className="max-w-4xl mx-auto">
                <div className="flex justify-between items-center mb-6">
                    <h1 className="text-2xl font-bold">Links</h1>
                    <button
                        onClick={refresh}
                        disabled={isLoading}
                        className="bg-blue-500 text-white px-4 py-2 rounded disabled:opacity-50"
                    >
                        {isLoading ? 'Refreshing...' : 'Refresh'}
                    </button>
                </div>

                {error ? (
                    <div className="bg-red-100 text-red-700 p-3 rounded mb-4">
                        {error}
                    </div>
                ) : null}

                <form onSubmit={handleSubmit} className="bg-white border rounded p-6 mb-6">
                    <h2 className="text-xl font-semibold mb-4">Create New Link</h2>

                    <div className="mb-4">
                        <label className="block mb-2 font-medium">URL</label>
                        <input
                            type="url"
                            value={url}
                            onChange={(e) => setUrl(e.target.value)}
                            placeholder="https://example.com"
                            className="w-full p-2 border rounded"
                            required
                        />
                    </div>

                    <div className="mb-4">
                        <label className="block mb-2 font-medium">Custom Slug (optional)</label>
                        <input
                            type="text"
                            value={customSlug}
                            onChange={(e) => setCustomSlug(e.target.value)}
                            placeholder="my-custom-slug"
                            className="w-full p-2 border rounded"
                        />
                    </div>

                    <button
                        type="submit"
                        disabled={isCreating}
                        className="w-full bg-green-500 text-white p-2 rounded disabled:opacity-50"
                    >
                        {isCreating ? 'Creating...' : 'Create Link'}
                    </button>
                </form>

                {isLoading && links.length === 0 ? (
                    <div className="text-center py-8">Loading links...</div>
                ) : links.length === 0 ? (
                    <div className="text-center py-8 text-gray-500">
                        No links yet. Create your first link!
                    </div>
                ) : (
                    <div className="space-y-4">
                        {links.map((link) => (
                            <div
                                key={link.id}
                                className="border rounded p-4 hover:bg-gray-50"
                            >
                                <div className="flex justify-between items-start">
                                    <div className="flex-1">
                                        <div className="font-semibold text-blue-600">
                                            /{link.slug}
                                        </div>
                                        <div className="text-sm text-gray-600 mt-1 break-all">
                                            {link.url}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    )
}
