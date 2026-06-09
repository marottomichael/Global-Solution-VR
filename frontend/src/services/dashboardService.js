import { request } from '../core/apiClient.js';

export function obterDashboardAmazonia() {
    return request('/dashboard/amazonia');
}
