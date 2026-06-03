import { request } from '../core/apiClient.js';

export function obterEstatisticas() {
    return request('/estatisticas');
}
