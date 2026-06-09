import { request } from '../core/apiClient.js';

export function criarSolicitacao(payload) {
    return request('/solicitacoes', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
}

export function obterSolicitacao(id) {
    return request(`/solicitacoes/${id}`);
}

export function listarPorCategoria(categoria) {
    return request(`/solicitacoes/categoria/${categoria}`);
}
