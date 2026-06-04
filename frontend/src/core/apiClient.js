const API_BASE = (import.meta.env.VITE_API_BASE_URL || '').replace(/\/$/, '');

export function apiUrl(path) {
    const normalized = path.startsWith('/') ? path : `/${path}`;
    return `${API_BASE}${normalized}`;
}

export async function request(path, options = {}) {
    const config = {
        headers: {
            Accept: 'application/json',
            ...options.headers
        },
        ...options
    };

    const response = await fetch(apiUrl(path), config);

    if (!response.ok) {
        const error = new Error(`Requisição falhou (${response.status})`);
        error.status = response.status;
        try {
            error.body = await response.json();
        } catch {
            error.body = null;
        }
        throw error;
    }

    if (response.status === 204) {
        return null;
    }

    return response.json();
}
