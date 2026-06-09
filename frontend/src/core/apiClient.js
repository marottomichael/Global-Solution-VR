const API_BASE = (import.meta.env.VITE_API_BASE_URL || '').replace(/\/$/, '');

export function apiUrl(path) {
    const normalized = path.startsWith('/') ? path : `/${path}`;
    return `${API_BASE}${normalized}`;
}

export async function request(path, options = {}) {
    const url = apiUrl(path);
    const config = {
        headers: {
            Accept: 'application/json',
            ...options.headers
        },
        ...options
    };

    let response;
    try {
        response = await fetch(url, config);
    } catch (cause) {
        const error = new Error('Não foi possível conectar ao backend.');
        error.code = 'NETWORK_ERROR';
        error.url = url;
        error.cause = cause;
        throw error;
    }

    if (!response.ok) {
        const error = new Error(`Requisição falhou (${response.status})`);
        error.status = response.status;
        error.url = url;
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
